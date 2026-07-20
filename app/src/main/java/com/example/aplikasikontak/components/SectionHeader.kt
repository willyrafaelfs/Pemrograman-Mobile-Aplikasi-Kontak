package com.example.aplikasikontak.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikasikontak.ui.theme.AplikasiKontakTheme

/**
 * ============================================================
 * CUSTOM COMPOSABLE #3: SectionHeader
 * ============================================================
 *
 * Header untuk mengelompokkan item di LazyColumn.
 * Digunakan bersama stickyHeader { } di LazyColumn.
 *
 * KONSEP BARU: SLOT API
 * ─────────────────────
 *
 * Apa itu Slot API?
 * Parameter yang menerima @Composable lambda → "slot kosong"
 * yang bisa diisi dengan composable APA SAJA oleh parent.
 *
 * Analogi: BINGKAI FOTO
 * - SectionHeader = bingkai dengan hiasan di pinggir
 * - trailingContent = slot kosong di pojok kanan
 * - Parent bisa mengisi slot dengan: Icon, Badge, Button, apa saja!
 *
 * Tanpa Slot API:
 *   SectionHeader(title = "A", count = 3)
 *   → Hanya bisa tampilkan count. Tidak fleksibel.
 *
 * Dengan Slot API:
 *   SectionHeader(title = "A") { Badge { Text("3") } }
 *   SectionHeader(title = "A") { Icon(Icons.Star, null) }
 *   SectionHeader(title = "A") { /* Kosong juga boleh! */ }
 *   → Bisa diisi APA SAJA. Super fleksibel!
 *
 * @param title Teks judul header (misal: "A", "B", "Keluarga")
 * @param modifier Modifier dari parent
 * @param trailingContent SLOT API — composable opsional di sebelah kanan
 */
@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    // ↓↓↓ INI ADALAH SLOT API ↓↓↓
    // @Composable () -> Unit = {} berarti:
    // - Menerima composable function sebagai parameter
    // - Default = {} (kosong/tidak ada konten)
    // - Parent BOLEH mengisi, BOLEH juga tidak
    trailingContent: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Badge huruf di kiri
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(32.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Garis pemisah
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.outlineVariant
        )

        Spacer(modifier = Modifier.width(12.dp))

        // ↓↓↓ SLOT DIISI DI SINI ↓↓↓
        // Apa pun yang parent kirim melalui trailingContent
        // akan di-render di posisi ini.
        trailingContent()
    }
}

/**
 * ============================================================
 * CUSTOM COMPOSABLE #4: SectionCard (Slot API Lanjutan)
 * ============================================================
 *
 * Card dengan judul dan area konten fleksibel (Slot API).
 * Bisa diisi dengan composable apa saja.
 *
 * Contoh Slot API yang lebih umum — digunakan untuk
 * membungkus konten yang berbeda-beda dalam layout Card.
 *
 * @param title Judul section
 * @param modifier Modifier dari parent
 * @param content SLOT — konten utama di dalam card
 */
@Composable
fun SectionCard(
    title: String,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit  // Slot dengan ColumnScope!
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            // ↓ Slot diisi di sini — parent bisa kirim APA SAJA ↓
            content()
        }
    }
}

// ── PREVIEW ──
@Preview(showBackground = true)
@Composable
fun SectionHeaderPreview() {
    AplikasiKontakTheme {
        Column {
            // Preview 1: Slot diisi dengan Badge
            SectionHeader(title = "A") {
                Badge(
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Text("3", fontSize = 11.sp, color = MaterialTheme.colorScheme.onPrimary)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Preview 2: Slot diisi dengan Icon
            SectionHeader(title = "F") {
                Icon(
                    Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Preview 3: Slot KOSONG (default)
            SectionHeader(title = "Z")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SectionCardPreview() {
    AplikasiKontakTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            // SectionCard diisi dengan Text
            SectionCard(title = "📊 Statistik") {
                Text("Total kontak: 15")
                Text("Favorit: 4")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // SectionCard diisi dengan Row
            SectionCard(title = "📋 Info") {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Info, null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Konten fleksibel via Slot API!")
                }
            }
        }
    }
}
