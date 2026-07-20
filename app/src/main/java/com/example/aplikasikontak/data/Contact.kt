package com.example.aplikasikontak.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * ============================================================
 * DATA CLASS: Contact (Model Data Kontak)
 * ============================================================
 *
 * Data class otomatis menghasilkan:
 * - equals(), hashCode() → perbandingan objek
 * - toString() → untuk debug
 * - copy() → salinan dengan modifikasi
 * - componentN() → destructuring: val (id, name) = contact
 *
 * Ini adalah "cetak biru" untuk setiap kontak di aplikasi.
 */
data class Contact(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String = "",
    val category: ContactCategory = ContactCategory.OTHER,
    val isFavorite: Boolean = false,
    val avatarColor: Long = 0xFF6366F1  // Warna default avatar
)

/**
 * ============================================================
 * ENUM CLASS: ContactCategory
 * ============================================================
 *
 * Enum = kumpulan nilai tetap yang sudah ditentukan.
 * Digunakan untuk mengelompokkan kontak.
 *
 * Setiap enum memiliki:
 * - label → teks yang ditampilkan di UI
 * - icon → icon Material untuk visual
 */
enum class ContactCategory(val label: String, val icon: ImageVector) {
    ALL("Semua", Icons.Default.Groups),
    FAVORITE("Favorit", Icons.Default.Star),
    FAMILY("Keluarga", Icons.Default.Home),
    WORK("Kerja", Icons.Default.Work),
    FRIEND("Teman", Icons.Default.Handshake),
    OTHER("Lainnya", Icons.Default.PhoneAndroid);
}

/**
 * ============================================================
 * DATA DUMMY: Daftar kontak contoh
 * ============================================================
 *
 * Di aplikasi nyata, data datang dari database atau API.
 * Untuk Week 4, kita pakai data dummy untuk fokus belajar UI.
 *
 * Perhatikan warna avatar yang berbeda-beda → menunjukkan
 * bagaimana data mempengaruhi tampilan (data-driven UI).
 */
object ContactData {

    private val contacts = listOf(
        // === KELUARGA ===
        Contact(1, "Ayah", "+62 812-1111-1111", "ayah@mail.com",
            ContactCategory.FAMILY, isFavorite = true, avatarColor = 0xFF8B5CF6),
        Contact(2, "Ibu", "+62 812-2222-2222", "ibu@mail.com",
            ContactCategory.FAMILY, isFavorite = true, avatarColor = 0xFFF43F5E),
        Contact(3, "Adik Rini", "+62 813-3333-3333", "rini@mail.com",
            ContactCategory.FAMILY, avatarColor = 0xFFEC4899),

        // === KERJA ===
        Contact(4, "Bapak Kukuh (Dosen)", "+62 857-4444-4444", "kukuh@univ.ac.id",
            ContactCategory.WORK, isFavorite = true, avatarColor = 0xFF6366F1),
        Contact(5, "Pak Budi (Pembimbing)", "+62 878-5555-5555", "budi@univ.ac.id",
            ContactCategory.WORK, avatarColor = 0xFF0EA5E9),
        Contact(6, "HRD PT Teknologi", "+62 821-6666-6666", "hrd@teknologi.co.id",
            ContactCategory.WORK, avatarColor = 0xFF14B8A6),

        // === TEMAN ===
        Contact(7, "Ahmad Fauzi", "+62 812-7777-7777", "ahmad@gmail.com",
            ContactCategory.FRIEND, isFavorite = true, avatarColor = 0xFFF59E0B),
        Contact(8, "Dewi Lestari", "+62 813-8888-8888", "dewi@gmail.com",
            ContactCategory.FRIEND, avatarColor = 0xFF10B981),
        Contact(9, "Eko Prasetyo", "+62 857-9999-9999", "eko@gmail.com",
            ContactCategory.FRIEND, avatarColor = 0xFF8B5CF6),
        Contact(10, "Fitri Handayani", "+62 878-1010-1010", "fitri@gmail.com",
            ContactCategory.FRIEND, avatarColor = 0xFFEF4444),
        Contact(11, "Galih Nugroho", "+62 821-1111-2222", "galih@gmail.com",
            ContactCategory.FRIEND, avatarColor = 0xFF6366F1),
        Contact(12, "Hana Safira", "+62 812-1212-1212", "hana@gmail.com",
            ContactCategory.FRIEND, avatarColor = 0xFFEC4899),

        // === LAINNYA ===
        Contact(13, "Apotek Sehat", "+62 21-555-1234", "",
            ContactCategory.OTHER, avatarColor = 0xFF14B8A6),
        Contact(14, "Bengkel Motor Jaya", "+62 21-555-5678", "",
            ContactCategory.OTHER, avatarColor = 0xFF64748B),
        Contact(15, "Indihome Customer", "147", "",
            ContactCategory.OTHER, avatarColor = 0xFFEF4444),
    )

    /** Semua kontak */
    fun getAll(): List<Contact> = contacts

    /** Filter berdasarkan kategori */
    fun getByCategory(category: ContactCategory): List<Contact> {
        return when (category) {
            ContactCategory.ALL -> contacts
            ContactCategory.FAVORITE -> contacts.filter { it.isFavorite }
            else -> contacts.filter { it.category == category }
        }
    }

    /**
     * Kelompokkan kontak berdasarkan huruf awal nama.
     * Digunakan untuk Sticky Headers.
     *
     * Contoh output:
     * "A" → [Ahmad Fauzi, Adik Rini, Apotek Sehat]
     * "B" → [Bapak Kukuh, Bengkel Motor]
     * ...
     *
     * groupBy { } → fungsi Kotlin yang mengelompokkan list
     * berdasarkan kriteria tertentu. Hasilnya = Map<Key, List>
     */
    fun getGroupedByInitial(category: ContactCategory = ContactCategory.ALL): Map<String, List<Contact>> {
        return getByCategory(category)
            .sortedBy { it.name }                      // Urutkan A-Z
            .groupBy { it.name.first().uppercase() }   // Kelompokkan per huruf
    }
}
