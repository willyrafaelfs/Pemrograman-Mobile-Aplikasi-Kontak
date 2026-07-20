package com.example.aplikasikontak.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikasikontak.data.ContactCategory
import com.example.aplikasikontak.ui.theme.AplikasiKontakTheme

/**
 * ============================================================
 * CUSTOM COMPOSABLE #1: CategoryChip
 * ============================================================
 *
 * CategoryChip adalah komponen REUSABLE untuk menampilkan
 * satu kategori kontak (Semua, Favorit, Keluarga, dll).
 *
 * KONSEP YANG DITERAPKAN:
 *
 * 1. PARAMETER DESIGN (Desain Parameter)
 *    ─────────────────────────────────────
 *    Setiap Custom Composable harus memikirkan:
 *    a. Data apa yang dibutuhkan? → category, isSelected
 *    b. Aksi apa yang bisa dilakukan? → onClick
 *    c. Apa yang bisa dikustomisasi? → modifier
 *
 * 2. MODIFIER SEBAGAI PARAMETER
 *    ──────────────────────────────
 *    SELALU terima modifier: Modifier = Modifier sebagai parameter.
 *    Default = Modifier kosong (tanpa modifikasi).
 *
 *    Mengapa?
 *    - Parent bisa menambah padding, size, dll dari luar
 *    - Composable tetap fleksibel tanpa hardcode layout
 *
 *    Contoh: CategoryChip(modifier = Modifier.padding(8.dp))
 *    → Chip akan memiliki padding 8dp dari luar
 *
 * 3. CONDITIONAL STYLING
 *    ────────────────────
 *    Tampilan berubah berdasarkan state (isSelected).
 *    - Selected → warna primary (bold, menonjol)
 *    - Not selected → warna surface variant (netral)
 *
 * @param category Kategori kontak yang ditampilkan
 * @param isSelected Apakah chip ini sedang dipilih/aktif
 * @param onClick Callback saat chip di-klik
 * @param modifier Modifier dari parent (default: kosong)
 */
@Composable
fun CategoryChip(
    category: ContactCategory,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier    // ← BEST PRACTICE: selalu ada!
) {
    // FilterChip = Material3 chip yang bisa di-toggle
    // Punya state selected/unselected dengan visual berbeda
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = {
            Text(
                text = category.label,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        },
        leadingIcon = {
            Icon(
                imageVector = category.icon,
                contentDescription = null,
                modifier = Modifier.size(FilterChipDefaults.IconSize)
            )
        },
        modifier = modifier.height(36.dp),
        shape = RoundedCornerShape(18.dp),
        colors = FilterChipDefaults.filterChipColors(
            // Warna saat DIPILIH
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            // Warna saat TIDAK DIPILIH
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}

// ── PREVIEW ──
// Karena Custom Composable, kita bisa preview sendiri-sendiri!
// Ini keuntungan membuat komponen yang reusable.

@Preview(showBackground = true)
@Composable
fun CategoryChipSelectedPreview() {
    AplikasiKontakTheme {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CategoryChip(
                category = ContactCategory.ALL,
                isSelected = true,
                onClick = {}
            )
            CategoryChip(
                category = ContactCategory.FAVORITE,
                isSelected = false,
                onClick = {}
            )
        }
    }
}
