package com.example.room

import android.app.Application
import androidx.room.Room

class App : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        db = Room.inMemoryDatabaseBuilder(
            this,
            AppDatabase::class.java
        ).fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        lateinit var INSTANCE: App
            private set
    }

}