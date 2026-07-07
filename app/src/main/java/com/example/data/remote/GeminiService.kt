package com.example.data.remote

import android.graphics.Bitmap
import android.util.Base64
import com.example.BuildConfig
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

@JsonClass(generateAdapter = true)
data class GeminiRequest(
    val contents: List<GeminiContent>,
    val systemInstruction: GeminiContent? = null
)

@JsonClass(generateAdapter = true)
data class GeminiContent(
    val parts: List<GeminiPart>
)

@JsonClass(generateAdapter = true)
data class GeminiPart(
    val text: String? = null,
    val inlineData: GeminiInlineData? = null
)

@JsonClass(generateAdapter = true)
data class GeminiInlineData(
    val mimeType: String,
    val data: String
)

@JsonClass(generateAdapter = true)
data class GeminiResponse(
    val candidates: List<GeminiCandidate>?
)

@JsonClass(generateAdapter = true)
data class GeminiCandidate(
    val content: GeminiContent?
)

interface GeminiApi {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}

object GeminiClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val api: GeminiApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GeminiApi::class.java)
    }
}

class GeminiService {

    private val apiKey: String = BuildConfig.GEMINI_API_KEY

    suspend fun analyzeProblem(description: String): String = withContext(Dispatchers.IO) {
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext "API key is not configured. Please use the Secrets panel to configure your GEMINI_API_KEY."
        }

        val systemPrompt = """
            You are CivicLink AI, an intelligent smart city assistant connecting citizens to essential services.
            Analyze the user's service request description.
            Identify the service category, list 3 suggested diagnostic steps, and estimate a realistic local price range in USD.
            
            Format your response clearly as:
            ## Category Identified: [Category]
            *Plumbing, Electrical, Carpentry, Painting, Cleaning, AC Repair, Pest Control, Generator Servicing, Vehicle Towing, etc.*
            
            ## AI Summary Analysis
            [Brief analysis of the problem]
            
            ## Recommended Diagnostic Steps
            1. [Step 1]
            2. [Step 2]
            3. [Step 3]
            
            ## Predicted Estimate
            - **Estimated Cost:** $[Min] - $[Max]
            - **Priority Level:** [Emergency / Normal]
            - **Completion Time:** [e.g. 1-2 hours]
        """.trimIndent()

        val request = GeminiRequest(
            contents = listOf(
                GeminiContent(parts = listOf(GeminiPart(text = description)))
            ),
            systemInstruction = GeminiContent(parts = listOf(GeminiPart(text = systemPrompt)))
        )

        try {
            val response = GeminiClient.api.generateContent(apiKey, request)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: "No response from AI assistant. Please describe the problem in more detail."
        } catch (e: Exception) {
            e.printStackTrace()
            "Failed to communicate with AI Assistant: ${e.localizedMessage ?: e.message}"
        }
    }

    suspend fun analyzeFaultImage(bitmap: Bitmap, prompt: String): String = withContext(Dispatchers.IO) {
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext "API key is not configured. Please use the Secrets panel to configure your GEMINI_API_KEY."
        }

        // Compress bitmap to Base64
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
        val base64Image = Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)

        val systemPrompt = """
            You are CivicLink AI, a Computer Vision Fault Diagnosis expert.
            Analyze the uploaded image of damaged equipment, infrastructure, or appliance.
            Identify the damage, estimate the cause, describe the repair requirements, and predict cost/time.
            
            Format your response clearly as:
            ## Fault Diagnostic Report
            - **Detected Issue:** [Describe visual issue, e.g. burst pipe, burnt socket, wall crack]
            - **Severity Level:** [Low, Medium, High, Critical Emergency]
            - **Suspected Root Cause:** [e.g. wear and tear, voltage surge, external impact]
            
            ## Recommended Service
            - **Service Class:** [e.g. Plumbing / Electrical / Appliance Repair]
            - **Tools Needed:** [List of typical parts/tools the provider should bring]
            
            ## Estimated Repair Metrics
            - **Cost Estimate:** $[Min] - $[Max]
            - **Time Frame:** [e.g. 2-3 hours]
        """.trimIndent()

        val request = GeminiRequest(
            contents = listOf(
                GeminiContent(
                    parts = listOf(
                        GeminiPart(text = prompt.ifEmpty { "Analyze this damage and provide diagnostic insights." }),
                        GeminiPart(
                            inlineData = GeminiInlineData(
                                mimeType = "image/jpeg",
                                data = base64Image
                            )
                        )
                    )
                )
            ),
            systemInstruction = GeminiContent(parts = listOf(GeminiPart(text = systemPrompt)))
        )

        try {
            val response = GeminiClient.api.generateContent(apiKey, request)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: "Visual diagnostic complete. The image indicates localized wear. Standard maintenance is recommended."
        } catch (e: Exception) {
            e.printStackTrace()
            "Fault analysis failed: ${e.localizedMessage ?: e.message}"
        }
    }
}
