package com.example.core.model.data.entity

data class LoginResponse(
    val message: String,
    val user: User?= null
)
