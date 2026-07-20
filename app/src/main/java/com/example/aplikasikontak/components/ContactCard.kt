package com.example.aplikasikontak.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikasikontak.data.Contact
import com.example.aplikasikontak.data.ContactCategory
import com.example.aplikasikontak.ui.theme.AplikasiKontakTheme

/**
 * ============================================================
 * CUSTOM COMPOSABLE #2: ContactCard
 * ============================================================
 *
 * Menampilkan satu kontak dalam bentuk Card.
 * Digunakan di dalam LazyColumn sebagai item list.
 *
 * KONSEP YANG DITERAPKAN:
 *
 * 1. SINGLE RESPONSIBILITY
 *    ──────────────────────
 *    ContactCard HANYA bertugas menampilkan data kontak.
 *    Tidak tahu tentang:
 *    - Dari mana data berasal (database? API?)
 *    - Apa yang terjadi saat di-klik (navigasi? dialog?)
 *    - Bagaimana posisinya di list (index? grouped?)
 *
 *    Tugas-tugas itu tanggung jawab PARENT.
 *
 * 2. DATA-DRIVEN UI
 *    ───────────────
 *    Semua tampilan ditentukan oleh data Contact:
 *    - Warna avatar → dari contact.avatarColor
 *    - Huruf avatar → dari contact.name.first()
 *    - Icon favorit → dari contact.isFavorite
 *    - Badge kategori → dari contact.category
 *
 *    Ini artinya: ubah data → UI otomatis berubah!
 *
 * 3. COMPOSABLE COMPOSITION
 *    ──────────────────────
 *    ContactCard sendiri dibuat dari composable lain:
 *    Card → Row → [Avatar] + [Column (info)] + [Icon]
 *
 *    Ini seperti LEGO — composable besar dibuat dari
 *    composable kecil yang disusun bersama.
 *
 * @param contact Data kontak yang ditampilkan
 * @param onClick Callback saat card di-klik
 * @param modifier Modifier dari parent
 */
@Composable
fun ContactCard(
    contact: Contact,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ── 1. AVATAR ──
            // Box dengan huruf pertama nama di tengah.
            // Warna avatar berasal dari data (data-driven).
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(contact.avatarColor)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = contact.name.first().uppercase(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // ── 2. INFO KONTAK ──
            // Column berisi nama, telepon, dan badge kategori.
            // weight(1f) → mengisi sisa ruang yang tersedia.
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Nama
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = contact.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    // Bintang favorit (muncul jika isFavorite = true)
                    if (contact.isFavorite) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Favorit",
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xFFF59E0B)   // Warna amber/kuning
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                // Telepon
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = contact.phone,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Badge kategori
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.height(22.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Icon(
                            imageVector = contact.category.icon,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = contact.category.label,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            // ── 3. ARROW ICON ──
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Detail",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ── PREVIEW: Bisa preview satu card secara terpisah! ──
@Preview(showBackground = true)
@Composable
fun ContactCardPreview() {
    AplikasiKontakTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ContactCard(
                contact = Contact(
                    1, "Ahmad Fauzi", "+62 812-7777-7777",
                    "ahmad@gmail.com", ContactCategory.FRIEND,
                    isFavorite = true, avatarColor = 0xFFF59E0B
                )
            )
            ContactCard(
                contact = Contact(
                    2, "Ibu", "+62 812-2222-2222",
                    "ibu@mail.com", ContactCategory.FAMILY,
                    avatarColor = 0xFFF43F5E
                )
            )
        }
    }
}
