package com.example.core.model.repository

import com.example.core.model.data.entity.Credentials
import com.example.core.model.data.remote.ApiService
import com.example.core.utils.HashPassword
import javax.inject.Inject

class LoginRepository @Inject constructor(
//    private val userDao: UserDao,
    private val apiService: ApiService
) {
    suspend fun login(email: String, password: String): Boolean {
//        val user = userDao.getUserByEmail(email) ?: return false

        val hashedPassword = HashPassword.hashPassword(password)
//        return hashedPassword == user.hashedPassword

        val credentials: Credentials = Credentials(
            email = email,
            hashedPassword = hashedPassword
        )
        val loginResponse = apiService.login(credentials)

        return loginResponse?.user != null

    }
}