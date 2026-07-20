package com.example.aplikasikontak.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikasikontak.components.CategoryChip
import com.example.aplikasikontak.components.ContactCard
import com.example.aplikasikontak.components.ContactDetail
import com.example.aplikasikontak.components.SectionCard
import com.example.aplikasikontak.components.SectionHeader
import com.example.aplikasikontak.data.Contact
import com.example.aplikasikontak.data.ContactCategory
import com.example.aplikasikontak.data.ContactData
import com.example.aplikasikontak.ui.theme.AplikasiKontakTheme

/**
 * ============================================================
 * CONTACT LIST SCREEN: Halaman Utama Aplikasi Kontak
 * ============================================================
 *
 * SEMUA KONSEP WEEK 4 diterapkan di sini:
 *
 * 1. LazyColumn → Daftar kontak vertikal yang efisien
 * 2. LazyRow → Daftar kategori horizontal (filter chip)
 * 3. stickyHeader → Header huruf yang "lengket" saat scroll
 * 4. Custom Composable → ContactCard, CategoryChip, SectionHeader
 * 5. Material3 Theming → Warna, typography dari theme
 * 6. State Management → Kategori terpilih, search query
 *
 * STRUKTUR LAYOUT:
 * ┌─────────────────────────────┐
 * │ TopAppBar (judul + search)  │
 * ├─────────────────────────────┤
 * │ LazyColumn                  │
 * │ ├── item { SectionCard }    │ ← Statistik
 * │ ├── item { LazyRow }        │ ← Kategori filter
 * │ ├── stickyHeader("A")       │ ← Header huruf A
 * │ │   ├── ContactCard         │
 * │ │   └── ContactCard         │
 * │ ├── stickyHeader("B")       │ ← Header huruf B
 * │ │   └── ContactCard         │
 * │ └── ... dst                 │
 * └─────────────────────────────┘
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ContactListScreen(
    isDarkMode: Boolean = false,
    onThemeToggle: () -> Unit = {}
) {

    // ════════════════════════════════════════════
    // STATE (menggunakan rememberSaveable)
    // ════════════════════════════════════════════
    // rememberSaveable → bertahan saat rotasi layar
    // Kategori yang dipilih user disimpan di sini
    var selectedCategory by rememberSaveable {
        mutableStateOf(ContactCategory.ALL)
    }

    // State untuk fitur Search (Tugas Mandiri #1)
    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    // State untuk detail kontak (Tugas Mandiri #2)
    var selectedContactForDetail by remember {
        mutableStateOf<Contact?>(null)
    }

    // ════════════════════════════════════════════
    // DATA (dihitung ulang saat state berubah)
    // ════════════════════════════════════════════
    // derivedStateOf → hanya hitung ulang jika selectedCategory atau searchQuery berubah
    val groupedContacts by remember(selectedCategory, searchQuery) {
        derivedStateOf {
            ContactData.getByCategory(selectedCategory)
                .filter { it.name.contains(searchQuery, ignoreCase = true) }
                .sortedBy { it.name }
                .groupBy { it.name.first().uppercase() }
        }
    }

    val totalContacts = groupedContacts.values.flatten().size
    val allCategories = ContactCategory.entries  // Semua enum values

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ContactPage,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Kontak Saya",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                        Text(
                            "$totalContacts kontak",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 32.dp)
                        )
                    }
                },
                actions = {
                    // Switch manual untuk Dark Mode (Tugas Mandiri #5)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { onThemeToggle() },
                            thumbContent = {
                                Icon(
                                    imageVector = if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                                    contentDescription = null,
                                    modifier = Modifier.size(SwitchDefaults.IconSize)
                                )
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->

        // ── DIALOG DETAIL (Tugas Mandiri #2) ──
        if (selectedContactForDetail != null) {
            AlertDialog(
                onDismissRequest = { selectedContactForDetail = null },
                confirmButton = {
                    TextButton(onClick = { selectedContactForDetail = null }) {
                        Text("Tutup")
                    }
                },
                text = {
                    ContactDetail(
                        contact = selectedContactForDetail!!
                    )
                }
            )
        }

        // ════════════════════════════════════════
        // LazyColumn UTAMA
        // ════════════════════════════════════════
        // Ini adalah inti dari Week 4!
        // LazyColumn = RecyclerView versi Compose.
        // Hanya render item yang terlihat di layar.
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            // contentPadding = padding di LUAR seluruh list
            // Berbeda dengan Modifier.padding yang mempengaruhi scroll area
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {

            // ──────────────────────────────────
            // BAGIAN 1: Statistik (item tunggal)
            // ──────────────────────────────────
            item {
                SectionCard(
                    title = "Cari Kontak",
                    icon = Icons.Default.Search,
                    modifier = Modifier.padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Ketik nama kontak...") },
                        leadingIcon = { Icon(Icons.Default.Search, null) },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Clear, null)
                                }
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                }
            }

            item {
                // SectionCard menggunakan SLOT API!
                // Konten di dalam { } adalah slot yang kita isi.
                SectionCard(
                    title = "Ringkasan",
                    icon = Icons.Default.Summarize,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatItem(
                            icon = Icons.Default.People,
                            value = "${ContactData.getAll().size}",
                            label = "Total"
                        )
                        StatItem(
                            icon = Icons.Default.Star,
                            value = "${ContactData.getByCategory(ContactCategory.FAVORITE).size}",
                            label = "Favorit"
                        )
                        StatItem(
                            icon = Icons.Default.Home,
                            value = "${ContactData.getByCategory(ContactCategory.FAMILY).size}",
                            label = "Keluarga"
                        )
                        StatItem(
                            icon = Icons.Default.Work,
                            value = "${ContactData.getByCategory(ContactCategory.WORK).size}",
                            label = "Kerja"
                        )
                    }
                }
            }

            // ──────────────────────────────────────
            // BAGIAN 2: LazyRow untuk kategori filter
            // ──────────────────────────────────────
            // LazyRow di DALAM LazyColumn!
            // LazyRow = scroll horizontal (lazy/efisien)
            //
            // Penggunaan: filter, kategori, galeri foto,
            // rekomendasi horizontal, dll.
            item {
                LazyRow(
                    // contentPadding → padding kiri-kanan di luar items
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    // horizontalArrangement → jarak antar chip
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    // items() di LazyRow sama seperti di LazyColumn
                    // Bedanya: scroll horizontal
                    items(
                        items = allCategories,
                        key = { it.name }  // Key unik = nama enum
                    ) { category ->
                        // CategoryChip = Custom Composable kita!
                        CategoryChip(
                            category = category,
                            isSelected = category == selectedCategory,
                            onClick = { selectedCategory = category }
                        )
                    }
                }
            }

            // ──────────────────────────────────
            // BAGIAN 3: Grouped contacts + Sticky Headers
            // ──────────────────────────────────
            // stickyHeader = header yang "lengket" di atas
            // saat user scroll ke bawah.
            //
            // Alurnya:
            // 1. groupedContacts = Map<String, List<Contact>>
            //    Contoh: {"A": [Ahmad, Adik], "B": [Bapak, Bengkel]}
            //
            // 2. forEach → loop setiap grup
            //
            // 3. stickyHeader → header huruf (A, B, C...)
            //    Header ini "lengket" di atas saat scroll
            //
            // 4. items() → daftar kontak dalam grup tersebut

            if (groupedContacts.isEmpty()) {
                // Tampilkan pesan jika tidak ada kontak
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.SearchOff,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Tidak ada kontak",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                // Loop setiap grup (A, B, C, ...)
                groupedContacts.forEach { (initial, contactsInGroup) ->

                    // ── STICKY HEADER ──
                    // Header ini akan "lengket" di atas layar
                    // saat user scroll melewatinya.
                    //
                    // PENTING: stickyHeader butuh
                    // @OptIn(ExperimentalFoundationApi::class)
                    stickyHeader(key = "header_$initial") {
                        // SectionHeader = Custom Composable kita!
                        // Menggunakan SLOT API di trailingContent
                        SectionHeader(title = initial) {
                            // Slot diisi dengan Badge jumlah kontak
                            Badge(
                                containerColor = MaterialTheme.colorScheme.primary
                            ) {
                                Text(
                                    "${contactsInGroup.size}",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.padding(horizontal = 2.dp)
                                )
                            }
                        }
                    }

                    // ── ITEMS dalam grup ──
                    // items() dengan key dan contentType!
                    //
                    // key = ID unik untuk setiap item
                    //   → Compose bisa track perubahan efisien
                    //   → Animasi insert/delete jadi smooth
                    //
                    // contentType = tipe konten untuk reuse
                    //   → Compose bisa reuse composable dari tipe sama
                    //   → Performa lebih baik di mixed-type list
                    items(
                        items = contactsInGroup,
                        key = { it.id },                    // ← WAJIB!
                        contentType = { "contact_card" }    // ← OPTIMASI!
                    ) { contact ->
                        // ContactCard = Custom Composable kita!
                        ContactCard(
                            contact = contact,
                            onClick = { selectedContactForDetail = contact },
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                                .animateItem() // Animasi (Tugas Mandiri #3)
                        )
                    }
                }
            }
        }
    }
}

/**
 * ============================================================
 * COMPOSABLE KECIL: StatItem
 * ============================================================
 *
 * Composable sederhana yang tidak perlu file terpisah.
 * Menampilkan satu statistik: emoji + angka + label.
 *
 * TIPS: Untuk composable yang sangat sederhana (< 20 baris)
 * dan hanya dipakai di satu tempat, boleh ditulis di file
 * yang sama. Tidak perlu membuat file terpisah.
 */
@Composable
private fun StatItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    label: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(28.dp)
        )
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// ════════════════════════════════════════════
// PREVIEW
// ════════════════════════════════════════════
// Preview untuk Light Mode dan Dark Mode!
// Pastikan tampilan bagus di kedua mode.

@Preview(showBackground = true, showSystemUi = true, name = "Light Mode")
@Composable
fun ContactListScreenPreview() {
    AplikasiKontakTheme(darkTheme = false) {
        ContactListScreen(isDarkMode = false)
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Dark Mode")
@Composable
fun ContactListScreenDarkPreview() {
    AplikasiKontakTheme(darkTheme = true) {
        ContactListScreen(isDarkMode = true)
    }
}
