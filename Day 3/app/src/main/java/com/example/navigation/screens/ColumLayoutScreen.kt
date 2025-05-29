package com.example.navigation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun ColumnLayoutScreen(navController: NavController) {
    Scaffold(topBar = { DetailTopAppBar(navController = navController, title = "Colum Layout") }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            ColorBox(
                color = Color(0xFF90EE90),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            ColorBox(
                color = Color(0xFF3CB371),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            ColorBox(
                color = Color(0xFF2E8B57),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
        }
    }
}