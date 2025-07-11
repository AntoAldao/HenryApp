package com.example.core.model.repository

import android.content.Context
import android.net.Uri
import com.example.core.model.data.dao.UserDao
import com.example.core.model.data.entity.User
import com.example.core.utils.uploadImageToCloudinary
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    suspend fun addUser(user: User): Long {
        return userDao.insertUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    fun uploadUserImage(
        context: Context,
        imageUri: Uri,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        uploadImageToCloudinary(context, imageUri, onSuccess, onError)
    }
}
