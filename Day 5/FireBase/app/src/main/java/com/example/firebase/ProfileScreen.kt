package com.example.firebase

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    name: String?,
    email: String?,
    photoUrl: String?,
    onSignOut: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var selectedDate by remember { mutableStateOf("01/11/2005") }
    var showDatePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.logouth),
                    error = painterResource(id = R.drawable.logouth)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            ProfileInfoRow(label = "Name", value = name ?: "N/A")
            Divider()
            ProfileInfoRow(label = "Email", value = email ?: "N/A")
            Divider()
            ProfileInfoRow(
                label = "Date of Birth",
                value = selectedDate,
                showArrow = true,
                modifier = Modifier.clickable { showDatePicker = true }
            )
            Divider()
            Spacer(modifier = Modifier.weight(1f))

            // nút đăng xuất
            Button(
                onClick = onSignOut,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                        )
            ) {
                Text("Sign Out")
            }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    showDatePicker = false
                    datePickerState.selectedDateMillis?.let { millis ->
                        selectedDate = millis.toFormattedDateString()
                    }
                }) { Text("OK") }
            },
            dismissButton = {
                Button(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}


@Composable
fun ProfileInfoRow(
    label: String,
    value: String,
    showArrow: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontWeight = FontWeight.SemiBold, color = Color.Gray)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = value, fontSize = 16.sp)
        if (showArrow) {
            Icon(
                painter = painterResource(id = android.R.drawable.arrow_down_float),
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

private fun Long.toFormattedDateString(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(date)
}