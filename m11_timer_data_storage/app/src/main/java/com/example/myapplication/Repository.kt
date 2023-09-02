package com.example.myapplication

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.widget.Toast


private const val PREFERENCE_NAME = "preference_name"
private const val SHARED_PREFS_KEY = "shared_prefs_key"

class Repository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
    private var localData: String? = null
    private val editor: SharedPreferences.Editor = prefs.edit()

    private fun getDataFromSharedPreference(): String? {
        return prefs.getString(SHARED_PREFS_KEY, null)
    }

    private fun getDataFromLocalVariable(): String? {
        return localData
    }

    fun saveText(text: String) {
        editor.putString(SHARED_PREFS_KEY, text)
        editor.apply()
        localData = text

    }

    fun clearText() {
        localData = null
        editor.clear()
        editor.apply()
    }

    fun getText(): String {
        return when {
            getDataFromLocalVariable() != null -> getDataFromLocalVariable()!!
            getDataFromSharedPreference() != null -> getDataFromSharedPreference()!!
            else -> "Sorry, there are no saved data"
        }
    }
}