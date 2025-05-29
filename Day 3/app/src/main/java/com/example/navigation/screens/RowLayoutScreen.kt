package com.example.navigation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun RowLayoutScreen(navController: NavController) {
    Scaffold(topBar = { DetailTopAppBar(navController = navController, title = "Row Layout") }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ColorBox(color = Color(0xFFADD8E6), modifier = Modifier.size(80.dp))
                ColorBox(color = Color(0xFFADD8E6), modifier = Modifier.size(80.dp))
                ColorBox(color = Color(0xFFADD8E6), modifier = Modifier.size(80.dp))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ColorBox(color = Color(0xFF87CEEB), modifier = Modifier.size(80.dp))
                ColorBox(color = Color(0xFF87CEEB), modifier = Modifier.size(80.dp))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ColorBox(color = Color(0xFF6495ED), modifier = Modifier.size(80.dp))
                ColorBox(color = Color(0xFF6495ED), modifier = Modifier.size(80.dp))
                ColorBox(color = Color(0xFF6495ED), modifier = Modifier.size(80.dp))
            }
        }
    }
}