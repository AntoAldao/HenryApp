package com.example.henryapp.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.data.entity.User
import com.example.core.model.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> get() = _user

    fun loadUser(email: String) {
        viewModelScope.launch {
            _user.value = userRepository.getUserByEmail(email)
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
                    userRepository.uploadUserImage(
                        context, imageUri,
                        onSuccess = { url ->
                            val updatedUser = currentUser.copy(
                                name = name,
                                lastName = lastName,
                                nationality = nationality,
                                imageUrl = url
                            )
                            viewModelScope.launch {
                                saveUser(updatedUser, onSuccess, onError)
                            }
                        },
                        onError = { error ->
                            onError("Error al subir la imagen: $error")
                        }
                    )
                } else {
                    val updatedUser = currentUser.copy(
                        name = name,
                        lastName = lastName,
                        nationality = nationality
                    )
                    viewModelScope.launch {
                        saveUser(updatedUser, onSuccess, onError)
                    }
                }
            } ?: onError("Usuario no encontrado")
        }
    }

    private suspend fun saveUser(
        updatedUser: User,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            userRepository.updateUser(updatedUser)
            _user.value = updatedUser
            onSuccess()
        } catch (e: Exception) {
            onError("Error al guardar los cambios: ${e.message}")
        }
    }
}