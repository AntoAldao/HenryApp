package com.example.henryapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.henryapp.preferences.ThemePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    private val _isDarkTheme = MutableStateFlow(ThemePreference.isDarkMode(application))
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    fun toggleTheme() {
        val newValue = !_isDarkTheme.value
        _isDarkTheme.value = newValue
        ThemePreference.setDarkMode(getApplication(), newValue)
    }
}
