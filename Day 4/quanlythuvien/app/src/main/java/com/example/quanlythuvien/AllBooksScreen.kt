package com.example.quanlythuvien

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllBooksScreen(
    navController: NavController,
    viewModel: LibraryViewModel
) {
    val allBooksList = viewModel.allBooks
    val currentStudent by viewModel.currentStudent

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Danh Sách Tất Cả Sách", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(ScreenRoutes.ADD_BOOK_SCREEN)
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Thêm sách mới vào thư viện")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(allBooksList, key = { it.id }) { book ->
                val isBorrowedByCurrentUser = currentStudent?.borrowedBookIds?.contains(book.id) == true
                LibraryBookItem(
                    book = book,
                    isBorrowedByCurrentUser = isBorrowedByCurrentUser,
                    onBorrowClick = {
                        currentStudent?.let { student ->
                            viewModel.borrowBook(student.id, book.id)
                        }
                    },
                    onDeleteClick = {
                        viewModel.deleteBook(book)
                    }
                )
            }
        }
    }
}

@Composable
fun LibraryBookItem(
    book: Book,
    isBorrowedByCurrentUser: Boolean,
    onBorrowClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Row {
                if (isBorrowedByCurrentUser) {
                    Text("Đã mượn", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodySmall)
                } else {
                    Button(onClick = onBorrowClick, modifier = Modifier.heightIn(min = 36.dp)) {
                        Text("Mượn")
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Filled.Delete, contentDescription = "Xóa sách khỏi thư viện", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
