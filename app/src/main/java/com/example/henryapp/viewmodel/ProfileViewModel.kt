package com.example.henryapp.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.henryapp.model.data.entity.User
import com.example.henryapp.model.data.dao.UserDao
import com.example.henryapp.utils.uploadImageToCloudinary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userDao: UserDao
) : ViewModel() {

    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> get() = _user

    fun loadUser(email: String) {
        viewModelScope.launch {
            _user.value = userDao.getUserByEmail(email)
        }
    }

    fun updateUser(
        context: Context,
        name: String,
        lastName: String,
        nationality: String,
        imageUri: Uri?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            _user.value?.let { currentUser ->
                if (imageUri != null) {
                    uploadImageToCloudinary(context, imageUri,
                        onSuccess = { url ->
                            val updatedUser = currentUser.copy(
                                name = name,
                                lastName = lastName,
                                nationality = nationality,
                                imageUrl = url
                            )
                            viewModelScope.launch {
                                saveUpdatedUser(updatedUser, onSuccess, onError)
                            }
                        },
                        onError = { error ->
                            onError("Error al subir la imagen: $error")
                        }
                    )
                } else {
                    // Si no hay imagen, actualiza solo los campos de texto
                    val updatedUser = currentUser.copy(
                        name = name,
                        lastName = lastName,
                        nationality = nationality
                    )
                    saveUpdatedUser(updatedUser, onSuccess, onError)
                }
            } ?: onError("Usuario no encontrado")
        }
    }

    private suspend fun saveUpdatedUser(
        updatedUser: User,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            userDao.updateUser(updatedUser)
            _user.value = updatedUser
            onSuccess()
        } catch (e: Exception) {
            onError("Error al guardar los cambios: ${e.message}")
        }
    }


}
