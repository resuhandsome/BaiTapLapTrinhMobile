package com.example.firebase

data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?,
    val email: String?
)

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)