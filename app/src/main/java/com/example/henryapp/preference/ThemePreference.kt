package com.example.henryapp.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object ThemePreference {
    private const val PREF_NAME = "theme_pref"
    private const val KEY_DARK_MODE = "dark_mode"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun isDarkMode(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_DARK_MODE, false)
    }

    fun setDarkMode(context: Context, isDark: Boolean) {
        getPrefs(context).edit { putBoolean(KEY_DARK_MODE, isDark) }
    }
}
