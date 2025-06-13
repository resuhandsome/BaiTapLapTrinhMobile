package com.example.quanlythuvien

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete // Icon để xóa sách đã mượn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: LibraryViewModel
) {
    val currentStudent by viewModel.currentStudent
    val borrowedBooks = viewModel.getBorrowedBooksForCurrentStudent()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hệ thống Quản lý Thư viện", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Mượn Sách") },
                icon = { Icon(Icons.Filled.Add, contentDescription = "Mượn sách mới") },
                onClick = {

                    navController.navigate(BottomNavItem.AllBooks.route)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            currentStudent?.let { student ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Sinh viên", style = MaterialTheme.typography.titleSmall, color = Color.Gray)
                        Text(student.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Medium)
                    }
                    Button(onClick = {
                        navController.navigate(BottomNavItem.Students.route)
                    }) {
                        Text("Thay đổi")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text("Sách đã mượn (${borrowedBooks.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))

                if (borrowedBooks.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Bạn chưa mượn quyển sách nào",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Nhấn \"Mượn Sách\" để bắt đầu hành trình đọc sách!",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(borrowedBooks, key = { it.id }) { book ->
                            BorrowedBookItem(
                                book = book,
                                onReturnBook = {
                                    viewModel.returnBook(student.id, book.id)
                                }
                            )
                        }
                    }
                }
            } ?: run {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Vui lòng chọn một sinh viên từ mục 'Sinh viên'.")
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
fun BorrowedBookItem(book: Book, onReturnBook: () -> Unit) {
    var isChecked by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                isChecked = it
                if (!isChecked) { // bỏ check là trả sách
                    onReturnBook()
                }
            }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = book.title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = onReturnBook) {
            Icon(Icons.Filled.Delete, contentDescription = "Trả sách", tint = MaterialTheme.colorScheme.error)
        }
    }
}
