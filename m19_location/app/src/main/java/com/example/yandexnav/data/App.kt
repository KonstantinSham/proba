package com.example.yandexnav.data

import android.app.Application
import com.example.yandexnav.presentation.MapFragment
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView


class App : Application(){
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(API_KEY)
    }
    companion object{
        private const val API_KEY = "a9489bda-d071-4d55-9a12-067fae42f626"
    }
}