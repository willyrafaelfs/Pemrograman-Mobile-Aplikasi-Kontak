package com.example.aplikasikontak

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.aplikasikontak.screens.ContactListScreen
import com.example.aplikasikontak.ui.theme.AplikasiKontakTheme

/**
 * MainActivity - Entry point aplikasi.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // State untuk dark mode manual (Tugas Mandiri #5)
            var isDarkMode by remember { mutableStateOf(false) }

            AplikasiKontakTheme(darkTheme = isDarkMode) {
                ContactListScreen(
                    isDarkMode = isDarkMode,
                    onThemeToggle = { isDarkMode = !isDarkMode }
                )
            }
        }
    }
}
