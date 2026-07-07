package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class Booking(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,
    val problemDescription: String,
    val timestamp: Long = System.currentTimeMillis(),
    val address: String,
    val citizenName: String,
    val isEmergency: Boolean = false,
    val status: String = "Pending", // "Pending", "Assigned", "In Progress", "Completed"
    val assignedProviderId: Int? = null,
    val assignedProviderName: String? = null,
    val estimatedCost: Double = 0.0,
    val completionTimeEstimate: String = "1-2 hours",
    val imageUrl: String? = null, // Base64 or local path
    val diagnosticReport: String? = null, // AI Computer Vision Diagnostic Report
    val rating: Int = 0, // 0 means unrated
    val comment: String? = null,
    val isOrganizationBulk: Boolean = false,
    val organizationId: String? = null
)

@Entity(tableName = "service_providers")
data class ServiceProvider(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String,
    val rating: Double = 4.5,
    val experienceYears: Int = 3,
    val contact: String,
    val status: String = "Available", // "Available", "Busy", "Offline"
    val currentLatitude: Double = 37.7749,
    val currentLongitude: Double = -122.4194,
    val isVerified: Boolean = false,
    val earnings: Double = 0.0
)

@Entity(tableName = "complaints")
data class Complaint(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bookingId: Int,
    val userType: String, // "Citizen", "Provider", "Organization"
    val title: String,
    val details: String,
    val status: String = "Submitted", // "Submitted", "Under Investigation", "Resolved"
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "org_schedules")
data class OrgBulkMaintenanceSchedule(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val organizationName: String,
    val serviceType: String,
    val location: String,
    val recurrence: String, // "Weekly", "Monthly", "Quarterly", "Annually"
    val lastScheduledDate: Long = System.currentTimeMillis() - 7 * 24 * 3600 * 1000L,
    val nextScheduledDate: Long = System.currentTimeMillis() + 14 * 24 * 3600 * 1000L,
    val status: String = "Active" // "Active", "Paused", "Completed"
)
