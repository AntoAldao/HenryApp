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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> get() = _user

    private val _errorEvents = MutableSharedFlow<String>()
    val errorEvents = _errorEvents.asSharedFlow()

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    fun loadUser(email: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _user.value = userRepository.getUserByEmail(email)
            } catch (e: Exception) {
                _errorEvents.emit("Error al cargar el usuario")
            }
            finally {
                _isLoading.value = false
            }
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
            val currentUser = _user.value
            if (currentUser == null) {
                onError("Usuario no encontrado")
                return@launch
            }

            try {
                val updatedUser = if (imageUri != null) {
                    userRepository.uploadUserImage(
                        context,
                        imageUri,
                        onSuccess = { imageUrl ->
                            val userWithImage = currentUser.copy(
                                name = name,
                                lastName = lastName,
                                nationality = nationality,
                                imageUrl = imageUrl
                            )
                            viewModelScope.launch {
                                saveUser(userWithImage, onSuccess, onError)
                            }
                        },
                        onError = { uploadError ->
                            onError("Error al subir la imagen: $uploadError")
                        }
                    )
                    return@launch
                } else {
                    currentUser.copy(
                        name = name,
                        lastName = lastName,
                        nationality = nationality
                    )
                }

                saveUser(updatedUser, onSuccess, onError)


            } catch (e: Exception) {
                onError("Error al actualizar perfil: ${e.message}")
            }
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
