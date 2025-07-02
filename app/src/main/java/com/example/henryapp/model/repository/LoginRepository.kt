package com.example.henryapp.model.repository

import com.example.henryapp.model.data.dao.UserDao
import com.example.henryapp.utils.HashPassword

class LoginRepository(private val userDao: UserDao) {
    suspend fun login(email: String, password: String): Boolean {
        println(email)
        val user = userDao.getUserByEmail(email)
        println(user)
        if (user == null) {
            return false
        }

        val hashedPassword = HashPassword.hashPassword(password)
        return hashedPassword == user.hashedPassword
    }
}