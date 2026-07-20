package com.example.aplikasikontak.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikasikontak.data.Contact
import com.example.aplikasikontak.data.ContactCategory
import com.example.aplikasikontak.ui.theme.AplikasiKontakTheme

/**
 * ============================================================
 * CUSTOM COMPOSABLE: ContactDetail
 * ============================================================
 *
 * Card besar yang menampilkan info lengkap kontak.
 * Tugas Mandiri #2.
 */
@Composable
fun ContactDetail(
    contact: Contact,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. Foto / Avatar Besar
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(contact.avatarColor)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = contact.name.first().uppercase(),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Nama & Kategori
            Text(
                text = contact.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = contact.category.icon,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = contact.category.label,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(modifier = Modifier.height(24.dp))

            // 3. Info Details
            DetailRow(icon = Icons.Default.Phone, label = "Telepon", value = contact.phone)
            if (contact.email.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                DetailRow(icon = Icons.Default.Email, label = "Email", value = contact.email)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Action Buttons (Optional but makes it look like a detail card)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilledTonalIconButton(onClick = { }) {
                    Icon(Icons.Default.Call, "Call")
                }
                FilledTonalIconButton(onClick = { }) {
                    Icon(Icons.Default.Message, "Message")
                }
                FilledTonalIconButton(onClick = { }) {
                    Icon(Icons.Default.VideoCall, "Video Call")
                }
                if (contact.isFavorite) {
                    FilledTonalIconButton(
                        onClick = { },
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = Color(0xFFFFECB3),
                            contentColor = Color(0xFFF59E0B)
                        )
                    ) {
                        Icon(Icons.Default.Star, "Favorite")
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactDetailPreview() {
    AplikasiKontakTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            ContactDetail(
                contact = Contact(
                    1, "Bapak Kukuh (Dosen)", "+62 857-4444-4444", "kukuh@univ.ac.id",
                    ContactCategory.WORK, isFavorite = true, avatarColor = 0xFF6366F1
                )
            )
        }
    }
}
