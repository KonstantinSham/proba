package com.example.skillbox_hw_quiz

import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        with(window) {
            enterTransition = Explode()
            exitTransition = Slide(Gravity.LEFT)
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NavHostFragment())
                .commitNow()
        }
    }
}