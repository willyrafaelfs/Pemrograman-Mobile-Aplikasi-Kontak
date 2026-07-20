package com.example.aplikasikontak.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

/**
 * ============================================================
 * MATERIAL3 THEMING: Sistem Warna Aplikasi
 * ============================================================
 *
 * Material3 menggunakan sistem "Color Roles" — setiap warna
 * punya FUNGSI tertentu, bukan hanya nama warna.
 *
 * LIGHT COLOR SCHEME:
 * ───────────────────
 * - primary       → Warna utama (tombol, link, FAB)
 * - onPrimary     → Teks/icon DI ATAS primary
 * - primaryContainer → Background ringan dari primary
 * - secondary     → Warna pendukung
 * - surface       → Background card, dialog
 * - surfaceVariant → Background alternatif (list header)
 * - background    → Background utama screen
 * - error         → Validasi & error
 *
 * "on" prefix = warna teks/icon yang tampil DI ATAS warna tersebut
 * Contoh: onPrimary = warna teks di atas background primary
 *
 * DARK COLOR SCHEME:
 * ──────────────────
 * Aturan dark mode Material3:
 * 1. Background → gelap (dark surface)
 * 2. Primary → lebih terang dari versi light
 * 3. Elevation → ditunjukkan dengan warna lebih terang (bukan shadow)
 * 4. Teks → tetap kontras dan mudah dibaca
 */

// ── WARNA LIGHT MODE ──
private val LightColors = lightColorScheme(
    primary = Color(0xFF6366F1),           // Indigo 500 - warna utama
    onPrimary = Color.White,               // Teks putih di atas primary
    primaryContainer = Color(0xFFE0E7FF),  // Indigo 100 - bg ringan
    onPrimaryContainer = Color(0xFF312E81),// Indigo 900 - teks gelap

    secondary = Color(0xFF14B8A6),         // Teal 500
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFCCFBF1),// Teal 100
    onSecondaryContainer = Color(0xFF134E4A),

    tertiary = Color(0xFFF59E0B),          // Amber 500
    onTertiary = Color.White,

    surface = Color(0xFFFFFFFF),           // Putih untuk card
    onSurface = Color(0xFF1E293B),         // Slate 800 untuk teks
    surfaceVariant = Color(0xFFF1F5F9),    // Slate 100 untuk header
    onSurfaceVariant = Color(0xFF64748B),  // Slate 500 untuk teks sekunder

    background = Color(0xFFFAFAFA),        // Gray 50
    onBackground = Color(0xFF1E293B),

    error = Color(0xFFEF4444),             // Red 500
    onError = Color.White,

    outline = Color(0xFFCBD5E1),           // Slate 300 - border
    outlineVariant = Color(0xFFE2E8F0),    // Slate 200 - divider ringan
)

// ── WARNA DARK MODE ──
// Perhatikan: warna-warna lebih terang untuk kontras!
private val DarkColors = darkColorScheme(
    primary = Color(0xFFA5B4FC),           // Indigo 300 (lebih terang!)
    onPrimary = Color(0xFF1E1B4B),
    primaryContainer = Color(0xFF3730A3),  // Indigo 800
    onPrimaryContainer = Color(0xFFE0E7FF),

    secondary = Color(0xFF5EEAD4),         // Teal 300
    onSecondary = Color(0xFF042F2E),
    secondaryContainer = Color(0xFF115E59),
    onSecondaryContainer = Color(0xFFCCFBF1),

    tertiary = Color(0xFFFCD34D),          // Amber 300
    onTertiary = Color(0xFF451A03),

    surface = Color(0xFF1E293B),           // Slate 800
    onSurface = Color(0xFFF1F5F9),         // Slate 100
    surfaceVariant = Color(0xFF334155),    // Slate 700
    onSurfaceVariant = Color(0xFF94A3B8),  // Slate 400

    background = Color(0xFF0F172A),        // Slate 900
    onBackground = Color(0xFFF1F5F9),

    error = Color(0xFFFCA5A5),             // Red 300
    onError = Color(0xFF7F1D1D),

    outline = Color(0xFF475569),           // Slate 600
    outlineVariant = Color(0xFF334155),
)

/**
 * ============================================================
 * COMPOSABLE THEME: Wrapper untuk seluruh aplikasi
 * ============================================================
 *
 * DYNAMIC COLOR (Android 12+):
 * Warna aplikasi otomatis mengikuti wallpaper pengguna!
 * Cukup gunakan dynamicLightColorScheme() / dynamicDarkColorScheme().
 * Jika device tidak mendukung → fallback ke warna manual.
 *
 * PENGGUNAAN:
 * Di MainActivity.kt:
 *   setContent {
 *     AplikasiKontakTheme {
 *       // Seluruh UI di sini akan mengikuti theme
 *       ContactListScreen()
 *     }
 *   }
 *
 * Di mana saja di dalam composable:
 *   MaterialTheme.colorScheme.primary  → warna primary
 *   MaterialTheme.typography.headlineMedium → typography
 *
 * @param darkTheme Apakah menggunakan dark mode
 * @param dynamicColor Apakah menggunakan dynamic color (Android 12+)
 * @param content Konten aplikasi (SLOT API!)
 */
@Composable
fun AplikasiKontakTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit  // ← Ini juga Slot API!
) {
    // Pilih color scheme berdasarkan kondisi
    val colorScheme = when {
        // Prioritas 1: Dynamic Color (jika device mendukung)
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        // Prioritas 2: Dark mode manual
        darkTheme -> DarkColors
        // Prioritas 3: Light mode manual
        else -> LightColors
    }

    // MaterialTheme = wrapper yang menyediakan warna & typography
    // ke SEMUA composable di dalamnya
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,  // Menggunakan custom typography yang baru dibuat
        content = content           // ← Konten aplikasi ditaruh di sini
    )
}
