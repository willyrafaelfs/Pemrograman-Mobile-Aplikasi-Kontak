package com.example.aplikasikontak.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * ============================================================
 * TYPOGRAPHY: Sistem Huruf Aplikasi
 * ============================================================
 *
 * Tugas Mandiri #4: Buat custom Typography (font berbeda heading & body).
 */
val Typography = Typography(
    // Style untuk Judul Besar
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Serif, // Menggunakan Serif untuk heading
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    
    // Style untuk Judul Screen/Section
    titleLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    // Style untuk Body (Teks Utama)
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif, // Menggunakan SansSerif untuk body
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    // Style untuk Label Kecil
    labelMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
