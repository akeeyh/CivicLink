package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.Booking
import com.example.data.model.Complaint
import com.example.data.model.OrgBulkMaintenanceSchedule
import com.example.data.model.ServiceProvider

@Database(
    entities = [
        Booking::class,
        ServiceProvider::class,
        Complaint::class,
        OrgBulkMaintenanceSchedule::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookingDao(): BookingDao
    abstract fun providerDao(): ProviderDao
    abstract fun complaintDao(): ComplaintDao
    abstract fun orgScheduleDao(): OrgScheduleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "civiclink_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
