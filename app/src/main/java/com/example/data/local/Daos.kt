package com.example.data.local

import androidx.room.*
import com.example.data.model.Booking
import com.example.data.model.Complaint
import com.example.data.model.OrgBulkMaintenanceSchedule
import com.example.data.model.ServiceProvider
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {
    @Query("SELECT * FROM bookings ORDER BY timestamp DESC")
    fun getAllBookings(): Flow<List<Booking>>

    @Query("SELECT * FROM bookings WHERE id = :id")
    suspend fun getBookingById(id: Int): Booking?

    @Query("SELECT * FROM bookings WHERE assignedProviderId = :providerId ORDER BY timestamp DESC")
    fun getBookingsForProvider(providerId: Int): Flow<List<Booking>>

    @Query("SELECT * FROM bookings WHERE isOrganizationBulk = 1 ORDER BY timestamp DESC")
    fun getOrganizationBookings(): Flow<List<Booking>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: Booking): Long

    @Update
    suspend fun updateBooking(booking: Booking)

    @Delete
    suspend fun deleteBooking(booking: Booking)

    @Query("DELETE FROM bookings WHERE id = :id")
    suspend fun deleteBookingById(id: Int)
}

@Dao
interface ProviderDao {
    @Query("SELECT * FROM service_providers ORDER BY rating DESC")
    fun getAllProviders(): Flow<List<ServiceProvider>>

    @Query("SELECT * FROM service_providers WHERE id = :id")
    suspend fun getProviderById(id: Int): ServiceProvider?

    @Query("SELECT * FROM service_providers WHERE category = :category AND status = 'Available'")
    suspend fun getAvailableProvidersByCategory(category: String): List<ServiceProvider>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProvider(provider: ServiceProvider): Long

    @Update
    suspend fun updateProvider(provider: ServiceProvider)

    @Query("UPDATE service_providers SET earnings = earnings + :amount WHERE id = :providerId")
    suspend fun addEarnings(providerId: Int, amount: Double)

    @Delete
    suspend fun deleteProvider(provider: ServiceProvider)
}

@Dao
interface ComplaintDao {
    @Query("SELECT * FROM complaints ORDER BY timestamp DESC")
    fun getAllComplaints(): Flow<List<Complaint>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComplaint(complaint: Complaint): Long

    @Update
    suspend fun updateComplaint(complaint: Complaint)
}

@Dao
interface OrgScheduleDao {
    @Query("SELECT * FROM org_schedules ORDER BY nextScheduledDate ASC")
    fun getAllSchedules(): Flow<List<OrgBulkMaintenanceSchedule>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: OrgBulkMaintenanceSchedule): Long

    @Update
    suspend fun updateSchedule(schedule: OrgBulkMaintenanceSchedule)

    @Delete
    suspend fun deleteSchedule(schedule: OrgBulkMaintenanceSchedule)
}
