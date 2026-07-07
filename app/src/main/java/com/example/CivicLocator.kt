package com.example

import android.content.Context
import com.example.data.local.AppDatabase
import com.example.data.remote.GeminiService
import com.example.data.repository.ServiceRepository

object CivicLocator {
    @Volatile
    private var repository: ServiceRepository? = null

    fun getRepository(context: Context): ServiceRepository {
        return repository ?: synchronized(this) {
            val existing = repository
            if (existing != null) {
                existing
            } else {
                val db = AppDatabase.getInstance(context)
                val gemini = GeminiService()
                val repo = ServiceRepository(
                    bookingDao = db.bookingDao(),
                    providerDao = db.providerDao(),
                    complaintDao = db.complaintDao(),
                    orgScheduleDao = db.orgScheduleDao(),
                    geminiService = gemini
                )
                repository = repo
                repo
            }
        }
    }
}
