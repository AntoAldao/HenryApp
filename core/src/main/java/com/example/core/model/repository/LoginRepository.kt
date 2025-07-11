package com.example.core.model.repository

import com.example.core.model.data.dao.UserDao
import com.example.core.utils.HashPassword

class LoginRepository(private val userDao: UserDao) {
    suspend fun login(email: String, password: String): Boolean {
        val user = userDao.getUserByEmail(email) ?: return false

        val hashedPassword = HashPassword.hashPassword(password)
        return hashedPassword == user.hashedPassword
    }
}