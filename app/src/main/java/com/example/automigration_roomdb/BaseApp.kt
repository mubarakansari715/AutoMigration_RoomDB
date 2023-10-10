package com.example.automigration_roomdb

import android.app.Application
import com.example.automigration_roomdb.di.AppDatabase
import com.example.automigration_roomdb.di.AppDatabase.Companion.getDatabase

class BaseApp : Application() {

    companion object {
        lateinit var appDatabase: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        //init the room db
        appDatabase = getDatabase(this)
    }
}