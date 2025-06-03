package com.example.thuchanh2day4

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(navController: NavController) {
    val pages = listOf(
        OnboardingPage(
            imageRes = "https://img.upanh.tv/2025/06/03/image5b0b970dd9c073b0.png",
            title = "Easy Time Management",
            description = "With management based on priority and daily tasks, it will give you convenience in managing and determining the tasks that must be done first."
        ),
        OnboardingPage(
            imageRes = "https://img.upanh.tv/2025/06/03/image6304c135fe424307.png",
            title = "Increase Work Effectiveness",
            description = "Time management and the determination of more important tasks will give your job statistics better and always improve."
        ),
        OnboardingPage(
            imageRes = "https://img.upanh.tv/2025/06/03/image13aaef445ef4657e.png",
            title = "Reminder Notification",
            description = "The advantage of this application is that it also provides reminders for you so you don't forget to keep doing your assignments well and according to the time you have set."
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    fun navigateToMainApp() {
        navController.popBackStack()
        navController.navigate(ScreenRoute.MainApp.route)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    Row(
                        Modifier
                            .padding(start = 16.dp, top = 16.dp)
                            .wrapContentSize(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        pages.indices.forEach { index ->
                            PageIndicator(isSelected = pagerState.currentPage == index)
                        }
                    }
                },
                actions = {
                    if (pagerState.currentPage < pages.size -1) {
                        TextButton(onClick = { navigateToMainApp() }) {
                            Text("skip", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f),
                pageSize = PageSize.Fill
            ) { pageIndex ->
                OnboardingPageLayout(page = pages[pageIndex])
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp, top = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val isFirstPage = pagerState.currentPage == 0
                val isLastPage = pagerState.currentPage == pages.size - 1

                // Nút "Quay lại"
                if (!isFirstPage) {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        },
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF0F0F0))
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }

                // Nút next vs start
                Button(
                    onClick = {
                        if (isLastPage) {
                            navigateToMainApp()
                        } else {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    },
                    modifier = Modifier
                        .height(56.dp)
                        .then(

                            if (isFirstPage) Modifier.fillMaxWidth() else Modifier.weight(1f)
                        ),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF))
                ) {
                    AnimatedContent(targetState = isLastPage, label = "buttonText") { lastPage ->
                        Text(
                            text = if (lastPage) "Start" else "Next",
                            fontSize = 18.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OnboardingPageLayout(page: OnboardingPage) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .padding(bottom = 32.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(page.imageRes)
                .crossfade(true)
                .build(),
            contentDescription = page.title,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .aspectRatio(1.2f)
                .padding(bottom = 48.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text = page.title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = page.description,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun PageIndicator(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .size(width = if (isSelected) 20.dp else 8.dp, height = 8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(if (isSelected) Color(0xFF007AFF) else Color.LightGray.copy(alpha = 0.5f))
    )
}
