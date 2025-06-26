package com.example.firebase

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Task(
    @DocumentId
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    @ServerTimestamp
    val createdAt: Date? = null,
)