package com.example.core.model.repository

import android.content.Context
import android.net.Uri
import com.example.core.model.data.entity.User
import com.example.core.model.data.remote.ApiService
import com.example.core.utils.uploadImageToCloudinary
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getUserByEmail(email: String): User? {
        val user = apiService.getUserByEmail(email)
        return user
    }

    suspend fun addUser(user: User): User  {
        val userN = apiService.registerUser(user)
        return userN
    }

    suspend fun updateUser(user: User):User {
        return apiService.updateUser(user.email, user)
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
