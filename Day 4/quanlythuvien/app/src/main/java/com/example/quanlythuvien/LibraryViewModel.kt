package com.example.quanlythuvien

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LibraryViewModel : ViewModel() {

    val allBooks = mutableStateListOf<Book>()


    val students = mutableStateListOf<Student>()


    var currentStudent = mutableStateOf<Student?>(null)

    init {
        addStudent("Nguyen Van A")
        addStudent("Nguyen Thi B")
        addBook("Sách Lập Trình Android")
        addBook("Sách Kotlin Cơ Bản")
        if (students.isNotEmpty()) {
            currentStudent.value = students.first()
            if (allBooks.isNotEmpty()) {
                borrowBook(students.first().id, allBooks.first().id)
            }
        }
    }

    // sách
    fun addBook(title: String) {
        if (title.isNotBlank()) {
            allBooks.add(Book(title = title))
        }
    }

    fun deleteBook(book: Book) {
        allBooks.remove(book)

        students.forEach { student ->
            student.borrowedBookIds.remove(book.id)
        }
    }

    fun getBookById(bookId: String): Book? {
        return allBooks.find { it.id == bookId }
    }

    // sinh viên
    fun addStudent(name: String) {
        if (name.isNotBlank()) {
            val newStudent = Student(name = name)
            students.add(newStudent)
            if (currentStudent.value == null) {
                currentStudent.value = newStudent
            }
        }
    }

    fun deleteStudent(student: Student) {
        students.remove(student)
        if (currentStudent.value?.id == student.id) {
            currentStudent.value = students.firstOrNull()
        }
    }

    fun setCurrentStudentById(studentId: String) {
        currentStudent.value = students.find { it.id == studentId }
    }

// mượn và trả sách
    fun borrowBook(studentId: String, bookId: String) {
        val student = students.find { it.id == studentId }
        val book = allBooks.find { it.id == bookId }
        if (student != null && book != null && !student.borrowedBookIds.contains(bookId)) {
            val studentIndex = students.indexOf(student)
            val updatedBorrowedBookIds = student.borrowedBookIds.toMutableList().apply { add(bookId) }
            students[studentIndex] = student.copy(borrowedBookIds = updatedBorrowedBookIds)

            if (currentStudent.value?.id == studentId) {
                currentStudent.value = students[studentIndex]
            }
        }
    }

    fun returnBook(studentId: String, bookId: String) {
        val student = students.find { it.id == studentId }
        if (student != null && student.borrowedBookIds.contains(bookId)) {
            val studentIndex = students.indexOf(student)
            val updatedBorrowedBookIds = student.borrowedBookIds.toMutableList().apply { remove(bookId) }
            students[studentIndex] = student.copy(borrowedBookIds = updatedBorrowedBookIds)

            if (currentStudent.value?.id == studentId) {
                currentStudent.value = students[studentIndex]
            }
        }
    }

    fun getBorrowedBooksForCurrentStudent(): List<Book> {
        return currentStudent.value?.borrowedBookIds?.mapNotNull { bookId ->
            allBooks.find { it.id == bookId }
        } ?: emptyList()
    }
}
