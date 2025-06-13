package com.example.quanlythuvien

import java.util.UUID

data class Book(
    val id: String = UUID.randomUUID().toString(),
    val title: String
)

data class Student(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val borrowedBookIds: MutableList<String> = mutableListOf()
)
