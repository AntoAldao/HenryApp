package com.example.henryapp.preference
import android.content.Context
import androidx.core.content.edit

object SessionManager {
    private const val PREF_NAME = "henry_session"
    private const val KEY_USER_EMAIL = "user_email"

    fun saveUserEmail(context: Context, email: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit { putString(KEY_USER_EMAIL, email) }
    }

    fun getUserEmail(context: Context): String? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_USER_EMAIL, null)
    }

    fun clearUserEmail(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit { remove(KEY_USER_EMAIL) }
    }
}
