package com.example.firebase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Date

data class TaskListUiState(
    val taskList: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class CrudUiState(
    val isLoading: Boolean = false,
    val isTaskDone: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

class TaskViewModel : ViewModel() {
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    private val _taskListUiState = MutableStateFlow(TaskListUiState())
    val taskListUiState = _taskListUiState.asStateFlow()

    private val _crudUiState = MutableStateFlow(CrudUiState())
    val crudUiState = _crudUiState.asStateFlow()

    init {
        auth.addAuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser != null) {
                fetchTasks()
            } else {
                _taskListUiState.update { it.copy(taskList = emptyList()) }
            }
        }
    }

    private fun fetchTasks() {
        val userId = auth.currentUser?.uid ?: return
        _taskListUiState.update { it.copy(isLoading = true) }

        db.collection("tasks")
            .whereEqualTo("userId", userId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener(MetadataChanges.INCLUDE) { snapshots, e ->
                if (e != null) {
                    _taskListUiState.update { it.copy(isLoading = false, errorMessage = "Error: ${e.message}") }
                    return@addSnapshotListener
                }

                val tasks = snapshots?.map { document ->
                    val task = document.toObject(Task::class.java)
                    task.copy(id = document.id)
                } ?: emptyList()

                _taskListUiState.update { it.copy(taskList = tasks, isLoading = false) }
            }
    }


    fun addTask(title: String, description: String) = viewModelScope.launch {
        _crudUiState.update { it.copy(isLoading = true) }
        val userId = auth.currentUser?.uid
        if (userId == null) {
            _crudUiState.update { it.copy(isLoading = false, errorMessage = "User not logged in") }
            return@launch
        }

        // Tạo một đối tượng Task để gửi lên Firestore
        val taskToSend = Task(userId = userId, title = title, description = description, createdAt = null)

        try {
            // 1. Gửi task lên Firestore và đợi kết quả trả về để lấy ID
            val documentReference: DocumentReference = db.collection("tasks").add(taskToSend).await()
            val newTaskId = documentReference.id

            // 2. Tạo một đối tượng Task hoàn chỉnh (với ID và thời gian tạm thời) để cập nhật UI
            val taskForUi = Task(
                id = newTaskId,
                userId = userId,
                title = title,
                description = description,
                createdAt = Date() // Dùng thời gian hiện tại của máy để hiển thị ngay lập tức
            )

            // 3. Cập nhật trực tiếp UI State, thêm task mới vào đầu danh sách
            _taskListUiState.update { currentState ->
                val updatedList = listOf(taskForUi) + currentState.taskList
                currentState.copy(taskList = updatedList)
            }

            // 4. Đặt thông báo thành công
            _crudUiState.update { it.copy(isLoading = false, isTaskDone = true, successMessage = "Thêm công việc thành công!") }

        } catch (e: Exception) {
            _crudUiState.update { it.copy(isLoading = false, errorMessage = "Failed to add task: ${e.message}") }
        }
    }

    fun deleteTask(taskId: String) = viewModelScope.launch {
        _crudUiState.update { it.copy(isLoading = true) }
        try {
            db.collection("tasks").document(taskId).delete().await()

            _taskListUiState.update { currentState ->
                val updatedList = currentState.taskList.filterNot { it.id == taskId }
                currentState.copy(taskList = updatedList)
            }

            _crudUiState.update { it.copy(isLoading = false, successMessage = "Đã xóa công việc!") }

        } catch (e: Exception) {
            _crudUiState.update { it.copy(isLoading = false, errorMessage = "Failed to delete task: ${e.message}") }
        }
    }

    fun updateTask(taskId: String, title: String, description: String) = viewModelScope.launch {
        _crudUiState.update { it.copy(isLoading = true) }
        try {
            val taskUpdates = mapOf("title" to title, "description" to description)
            db.collection("tasks").document(taskId).update(taskUpdates).await()

            // Cập nhật UI ngay lập tức
            _taskListUiState.update { currentState ->
                val updatedList = currentState.taskList.map { task ->
                    if (task.id == taskId) {
                        task.copy(title = title, description = description)
                    } else {
                        task
                    }
                }
                currentState.copy(taskList = updatedList)
            }

            _crudUiState.update { it.copy(isLoading = false, isTaskDone = true, successMessage = "Cập nhật thành công!") }
        } catch (e: Exception) {
            _crudUiState.update { it.copy(isLoading = false, errorMessage = "Failed to update task: ${e.message}") }
        }
    }

    fun resetCrudState() {
        _crudUiState.update { CrudUiState() }
    }
}