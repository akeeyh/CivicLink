package com.example.data.repository

import android.graphics.Bitmap
import com.example.data.local.BookingDao
import com.example.data.local.ComplaintDao
import com.example.data.local.OrgScheduleDao
import com.example.data.local.ProviderDao
import com.example.data.model.Booking
import com.example.data.model.Complaint
import com.example.data.model.OrgBulkMaintenanceSchedule
import com.example.data.model.ServiceProvider
import com.example.data.remote.GeminiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class ServiceRepository(
    private val bookingDao: BookingDao,
    private val providerDao: ProviderDao,
    private val complaintDao: ComplaintDao,
    private val orgScheduleDao: OrgScheduleDao,
    private val geminiService: GeminiService
) {

    val allBookings: Flow<List<Booking>> = bookingDao.getAllBookings()
    val allProviders: Flow<List<ServiceProvider>> = providerDao.getAllProviders()
    val allComplaints: Flow<List<Complaint>> = complaintDao.getAllComplaints()
    val allSchedules: Flow<List<OrgBulkMaintenanceSchedule>> = orgScheduleDao.getAllSchedules()
    val organizationBookings: Flow<List<Booking>> = bookingDao.getOrganizationBookings()

    fun getBookingsForProvider(providerId: Int): Flow<List<Booking>> {
        return bookingDao.getBookingsForProvider(providerId)
    }

    suspend fun getBookingById(id: Int): Booking? {
        return bookingDao.getBookingById(id)
    }

    suspend fun createBooking(booking: Booking): Int {
        return bookingDao.insertBooking(booking).toInt()
    }

    suspend fun updateBooking(booking: Booking) {
        bookingDao.updateBooking(booking)
    }

    suspend fun deleteBookingById(id: Int) {
        bookingDao.deleteBookingById(id)
    }

    suspend fun createComplaint(complaint: Complaint) {
        complaintDao.insertComplaint(complaint)
    }

    suspend fun updateComplaint(complaint: Complaint) {
        complaintDao.updateComplaint(complaint)
    }

    suspend fun createSchedule(schedule: OrgBulkMaintenanceSchedule) {
        orgScheduleDao.insertSchedule(schedule)
    }

    suspend fun updateSchedule(schedule: OrgBulkMaintenanceSchedule) {
        orgScheduleDao.updateSchedule(schedule)
    }

    suspend fun deleteSchedule(schedule: OrgBulkMaintenanceSchedule) {
        orgScheduleDao.deleteSchedule(schedule)
    }

    // --- Provider Actions ---
    suspend fun registerProvider(provider: ServiceProvider): Int {
        return providerDao.insertProvider(provider).toInt()
    }

    suspend fun updateProvider(provider: ServiceProvider) {
        providerDao.updateProvider(provider)
    }

    suspend fun addProviderEarnings(providerId: Int, amount: Double) {
        providerDao.addEarnings(providerId, amount)
    }

    // --- Gemini AI Actions ---
    suspend fun analyzeProblem(description: String): String {
        return geminiService.analyzeProblem(description)
    }

    suspend fun analyzeFaultImage(bitmap: Bitmap, prompt: String): String {
        return geminiService.analyzeFaultImage(bitmap, prompt)
    }

    // --- Seeding helper ---
    suspend fun seedDatabaseIfEmpty() {
        // Check if providers are empty
        val currentProviders = allProviders.first()
        if (currentProviders.isEmpty()) {
            val defaultProviders = listOf(
                ServiceProvider(
                    name = "Marcus Vance",
                    category = "Plumbing",
                    rating = 4.8,
                    experienceYears = 8,
                    contact = "+1 (555) 382-9012",
                    status = "Available",
                    currentLatitude = 37.7749,
                    currentLongitude = -122.4194,
                    isVerified = true,
                    earnings = 1540.0
                ),
                ServiceProvider(
                    name = "Elena Rostova",
                    category = "Electrical",
                    rating = 4.9,
                    experienceYears = 6,
                    contact = "+1 (555) 249-1084",
                    status = "Available",
                    currentLatitude = 37.7833,
                    currentLongitude = -122.4167,
                    isVerified = true,
                    earnings = 2310.0
                ),
                ServiceProvider(
                    name = "Kenji Sato",
                    category = "Appliance Repair",
                    rating = 4.7,
                    experienceYears = 5,
                    contact = "+1 (555) 193-4720",
                    status = "Available",
                    currentLatitude = 37.7699,
                    currentLongitude = -122.4468,
                    isVerified = true,
                    earnings = 1120.0
                ),
                ServiceProvider(
                    name = "Sarah Jenkins",
                    category = "Deep Cleaning",
                    rating = 4.6,
                    experienceYears = 4,
                    contact = "+1 (555) 832-7519",
                    status = "Available",
                    currentLatitude = 37.8044,
                    currentLongitude = -122.4081,
                    isVerified = true,
                    earnings = 980.0
                ),
                ServiceProvider(
                    name = "Dave Briggs",
                    category = "Pest Control",
                    rating = 4.5,
                    experienceYears = 3,
                    contact = "+1 (555) 601-3829",
                    status = "Busy",
                    currentLatitude = 37.7599,
                    currentLongitude = -122.4368,
                    isVerified = true,
                    earnings = 650.0
                ),
                ServiceProvider(
                    name = "Arthur Pendelton",
                    category = "Carpentry",
                    rating = 4.9,
                    experienceYears = 12,
                    contact = "+1 (555) 472-8833",
                    status = "Available",
                    currentLatitude = 37.7892,
                    currentLongitude = -122.4014,
                    isVerified = true,
                    earnings = 4120.0
                )
            )
            for (p in defaultProviders) {
                providerDao.insertProvider(p)
            }
        }

        // Check schedules
        val currentSchedules = allSchedules.first()
        if (currentSchedules.isEmpty()) {
            val defaultSchedules = listOf(
                OrgBulkMaintenanceSchedule(
                    organizationName = "Grandview Heights Apartments",
                    serviceType = "Plumbing (Water Tank Cleaning)",
                    location = "Tower A and B",
                    recurrence = "Quarterly",
                    status = "Active"
                ),
                OrgBulkMaintenanceSchedule(
                    organizationName = "Cityside General Hospital",
                    serviceType = "Pest Control",
                    location = "Ground Floor Cafeteria and Kitchen",
                    recurrence = "Monthly",
                    status = "Active"
                ),
                OrgBulkMaintenanceSchedule(
                    organizationName = "Oakridge High School",
                    serviceType = "Electrical (Generator Servicing)",
                    location = "Main Power Plant Room",
                    recurrence = "Annually",
                    status = "Active"
                )
            )
            for (s in defaultSchedules) {
                orgScheduleDao.insertSchedule(s)
            }
        }

        // Check bookings
        val currentBookings = allBookings.first()
        if (currentBookings.isEmpty()) {
            val defaultBookings = listOf(
                Booking(
                    category = "Plumbing",
                    problemDescription = "Water leaking from master bathroom faucet. Flooding the cabinet.",
                    address = "Apt 4B, 55 Main St",
                    citizenName = "Jane Carter",
                    isEmergency = true,
                    status = "Assigned",
                    assignedProviderId = 1,
                    assignedProviderName = "Marcus Vance",
                    estimatedCost = 85.0,
                    completionTimeEstimate = "1 hour"
                ),
                Booking(
                    category = "Electrical",
                    problemDescription = "Kitchen sockets spark when a toaster is plugged in. Potential hazards.",
                    address = "104 Maple Ave",
                    citizenName = "Timothy Bell",
                    isEmergency = true,
                    status = "Pending",
                    estimatedCost = 120.0,
                    completionTimeEstimate = "2 hours"
                ),
                Booking(
                    category = "Deep Cleaning",
                    problemDescription = "Standard carpet cleaning for a three-bedroom apartment.",
                    address = "Apt 12, 88 Pine St",
                    citizenName = "Arthur Dent",
                    isEmergency = false,
                    status = "Completed",
                    assignedProviderId = 4,
                    assignedProviderName = "Sarah Jenkins",
                    estimatedCost = 150.0,
                    completionTimeEstimate = "3 hours",
                    rating = 5,
                    comment = "Sarah did an amazing job, carpets look and smell brand new!"
                )
            )
            for (b in defaultBookings) {
                bookingDao.insertBooking(b)
            }
        }
    }
}
