package com.example.navigation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.text.withStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TextDetailScreen(navController: NavController) {
    Scaffold(topBar = { DetailTopAppBar(navController = navController, title = "Text Detail") }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                fontSize = 28.sp,
                lineHeight = 42.sp,
                text = buildAnnotatedString {
                    append("The ")
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.LineThrough
                        )
                    )
                    {
                        append("quick")
                    }
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFFD2691E),
                            fontSize = 38.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    {
                        append("B")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFFD2691E),
                            fontSize = 32.sp
                        )
                    ) {
                        append("rown")
                    }
                    append("\n")
                    withStyle(
                        style = SpanStyle(
                            letterSpacing = 0.2.em
                        )
                    ) {
                        append("fox j u m p s ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic
                        )
                    ) {
                        append("over")
                    }
                    append("\n")
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                        )
                    ) {
                        append("the")
                    }
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            fontStyle = FontStyle.Italic
                        )
                    ) {
                        append("lazy")
                    }
                    append(" dog.")
                }
            )
        }
    }
}