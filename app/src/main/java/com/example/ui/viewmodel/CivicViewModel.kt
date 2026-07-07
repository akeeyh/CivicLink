package com.example.ui.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.Booking
import com.example.data.model.Complaint
import com.example.data.model.OrgBulkMaintenanceSchedule
import com.example.data.model.ServiceProvider
import com.example.data.repository.ServiceRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CivicViewModel(private val repository: ServiceRepository) : ViewModel() {

    init {
        // Seed the database with starting mock records if empty
        viewModelScope.launch {
            repository.seedDatabaseIfEmpty()
        }
    }

    // --- State Flows ---
    val allBookings: StateFlow<List<Booking>> = repository.allBookings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allProviders: StateFlow<List<ServiceProvider>> = repository.allProviders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allSchedules: StateFlow<List<OrgBulkMaintenanceSchedule>> = repository.allSchedules
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allComplaints: StateFlow<List<Complaint>> = repository.allComplaints
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val organizationBookings: StateFlow<List<Booking>> = repository.organizationBookings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- UI/UX States ---
    private val _aiAnalysisResult = MutableStateFlow<String?>(null)
    val aiAnalysisResult: StateFlow<String?> = _aiAnalysisResult.asStateFlow()

    private val _isAnalyzing = MutableStateFlow(false)
    val isAnalyzing: StateFlow<Boolean> = _isAnalyzing.asStateFlow()

    private val _imageDiagnosticResult = MutableStateFlow<String?>(null)
    val imageDiagnosticResult: StateFlow<String?> = _imageDiagnosticResult.asStateFlow()

    private val _isImageAnalyzing = MutableStateFlow(false)
    val isImageAnalyzing: StateFlow<Boolean> = _isImageAnalyzing.asStateFlow()

    private val _selectedBooking = MutableStateFlow<Booking?>(null)
    val selectedBooking: StateFlow<Booking?> = _selectedBooking.asStateFlow()

    // Active screen state inside portal
    private val _currentPortalTab = MutableStateFlow(0) // 0: Citizen, 1: Provider, 2: Organization, 3: Admin
    val currentPortalTab: StateFlow<Int> = _currentPortalTab.asStateFlow()

    // --- Booking Management ---
    fun setPortalTab(tab: Int) {
        _currentPortalTab.value = tab
    }

    fun selectBooking(booking: Booking?) {
        _selectedBooking.value = booking
    }

    fun bookNewService(
        category: String,
        problemDescription: String,
        address: String,
        citizenName: String,
        isEmergency: Boolean,
        estimatedCost: Double,
        completionTime: String,
        imageUrl: String? = null,
        diagnosticReport: String? = null
    ) {
        viewModelScope.launch {
            val booking = Booking(
                category = category,
                problemDescription = problemDescription,
                address = address,
                citizenName = citizenName,
                isEmergency = isEmergency,
                status = "Pending",
                estimatedCost = estimatedCost,
                completionTimeEstimate = completionTime,
                imageUrl = imageUrl,
                diagnosticReport = diagnosticReport
            )
            val newId = repository.createBooking(booking)
            
            // Auto dispatch routine simulation for interactive engagement
            simulateJobDispatch(newId)
        }
    }

    private fun simulateJobDispatch(bookingId: Int) {
        viewModelScope.launch {
            delay(4000) // 4 seconds delay to simulate local provider searching
            val booking = repository.getBookingById(bookingId) ?: return@launch
            if (booking.status == "Pending") {
                // Find first available provider matching category
                val providers = repository.allProviders.first()
                val matchingProvider = providers.firstOrNull { 
                    it.category.lowercase() == booking.category.lowercase() && it.status == "Available"
                } ?: providers.firstOrNull { it.status == "Available" }
                
                if (matchingProvider != null) {
                    val updatedBooking = booking.copy(
                        status = "Assigned",
                        assignedProviderId = matchingProvider.id,
                        assignedProviderName = matchingProvider.name
                    )
                    repository.updateBooking(updatedBooking)
                    _selectedBooking.value = updatedBooking

                    // Auto update provider status to busy
                    repository.updateProvider(matchingProvider.copy(status = "Busy"))

                    // Transition to In Progress after another delay
                    delay(8000)
                    val activeBooking = repository.getBookingById(bookingId) ?: return@launch
                    if (activeBooking.status == "Assigned") {
                        val inProgressBooking = activeBooking.copy(status = "In Progress")
                        repository.updateBooking(inProgressBooking)
                        _selectedBooking.value = inProgressBooking
                    }
                }
            }
        }
    }

    fun completeBookingAndPay(bookingId: Int, rating: Int, comment: String) {
        viewModelScope.launch {
            val booking = repository.getBookingById(bookingId) ?: return@launch
            val updatedBooking = booking.copy(
                status = "Completed",
                rating = rating,
                comment = comment
            )
            repository.updateBooking(updatedBooking)
            _selectedBooking.value = updatedBooking

            // Release provider back to available & credit earnings
            booking.assignedProviderId?.let { pid ->
                val providers = repository.allProviders.first()
                val provider = providers.firstOrNull { it.id == pid }
                if (provider != null) {
                    repository.updateProvider(
                        provider.copy(
                            status = "Available",
                            earnings = provider.earnings + booking.estimatedCost,
                            rating = ((provider.rating * 5) + rating) / 6.0 // update running rating
                        )
                    )
                }
            }
        }
    }

    // --- Provider Actions ---
    fun registerNewProvider(name: String, category: String, contact: String, experience: Int) {
        viewModelScope.launch {
            val provider = ServiceProvider(
                name = name,
                category = category,
                contact = contact,
                experienceYears = experience,
                status = "Available",
                isVerified = false
            )
            repository.registerProvider(provider)
        }
    }

    fun approveProvider(providerId: Int) {
        viewModelScope.launch {
            val providers = repository.allProviders.first()
            val provider = providers.firstOrNull { it.id == providerId } ?: return@launch
            repository.updateProvider(provider.copy(isVerified = true))
        }
    }

    // --- Organization Actions ---
    fun scheduleMaintenance(orgName: String, serviceType: String, location: String, recurrence: String) {
        viewModelScope.launch {
            val schedule = OrgBulkMaintenanceSchedule(
                organizationName = orgName,
                serviceType = serviceType,
                location = location,
                recurrence = recurrence
            )
            repository.createSchedule(schedule)
        }
    }

    fun createBulkOrgBooking(orgName: String, category: String, desc: String, address: String) {
        viewModelScope.launch {
            val booking = Booking(
                category = category,
                problemDescription = desc,
                address = address,
                citizenName = orgName,
                isEmergency = false,
                status = "Pending",
                isOrganizationBulk = true,
                organizationId = orgName.take(6).uppercase(),
                estimatedCost = 250.0, // bulk discount fixed estimate
                completionTimeEstimate = "Same day service"
            )
            val bid = repository.createBooking(booking)
            simulateJobDispatch(bid)
        }
    }

    // --- Complaints Actions ---
    fun fileComplaint(bookingId: Int, userType: String, title: String, details: String) {
        viewModelScope.launch {
            val complaint = Complaint(
                bookingId = bookingId,
                userType = userType,
                title = title,
                details = details
            )
            repository.createComplaint(complaint)
        }
    }

    // --- Gemini AI Analysis ---
    fun analyzeServiceNeed(description: String) {
        viewModelScope.launch {
            _isAnalyzing.value = true
            _aiAnalysisResult.value = null
            try {
                val result = repository.analyzeProblem(description)
                _aiAnalysisResult.value = result
            } catch (e: Exception) {
                _aiAnalysisResult.value = "Failed to analyze request: ${e.message}"
            } finally {
                _isAnalyzing.value = false
            }
        }
    }

    fun analyzeDamagePhoto(bitmap: Bitmap, userPrompt: String) {
        viewModelScope.launch {
            _isImageAnalyzing.value = true
            _imageDiagnosticResult.value = null
            try {
                val result = repository.analyzeFaultImage(bitmap, userPrompt)
                _imageDiagnosticResult.value = result
            } catch (e: Exception) {
                _imageDiagnosticResult.value = "Failed to analyze image: ${e.message}"
            } finally {
                _isImageAnalyzing.value = false
            }
        }
    }

    fun clearAIState() {
        _aiAnalysisResult.value = null
        _imageDiagnosticResult.value = null
    }
}

class CivicViewModelFactory(private val repository: ServiceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CivicViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CivicViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
