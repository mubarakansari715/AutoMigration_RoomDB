package com.example.automigration_roomdb.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.automigration_roomdb.model.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = true,

)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {

        @Volatile
        var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context, AppDatabase::class.java, "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}