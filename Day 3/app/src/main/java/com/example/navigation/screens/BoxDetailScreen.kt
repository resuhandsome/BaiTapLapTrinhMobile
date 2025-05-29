package com.example.navigation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BoxDetailScreen(navController: NavController) {
    Scaffold(topBar = { DetailTopAppBar(navController = navController, title = "Box Layout") }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        )
        {
            Text("Box với nội dung căn giữa")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            )
            {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.Blue)
                )
            }

            Text("Box xếp chồng các thành phần")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Yellow)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .size(100.dp)
                        .background(Color.Green)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(100.dp)
                        .background(Color.Red)
                )
            }
        }
    }
}