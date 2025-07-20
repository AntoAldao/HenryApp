package com.example.henryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.henryapp.navigation.MainApp
import com.example.henryapp.ui.theme.HenryAppTheme
import com.example.henryapp.viewmodel.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            HenryAppTheme(darkTheme = isDarkTheme) {
                MainApp(themeViewModel = themeViewModel)
            }
        }
    }
}
