package com.example.henryapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.data.entity.User
import com.example.core.model.repository.UserRepository
import com.example.core.utils.HashPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository

) : ViewModel() {

    val name = mutableStateOf("")
    val lastName = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val confirmPassword = mutableStateOf("")
    val nationality = mutableStateOf("")

    val isEmailValid = mutableStateOf(true)
    val isPasswordValid = mutableStateOf(true)
    val isPasswordMatch = mutableStateOf(true)
    val isFormValid = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    fun validateForm() {
        isEmailValid.value = email.value.contains("@")
        isPasswordValid.value = password.value.length >= 8
        isPasswordMatch.value = password.value == confirmPassword.value
        isFormValid.value = name.value.isNotBlank() &&
                lastName.value.isNotBlank() &&
                email.value.isNotBlank() &&
                password.value.isNotBlank() &&
                confirmPassword.value.isNotBlank() &&
                nationality.value.isNotBlank() &&
                isEmailValid.value &&
                isPasswordValid.value &&
                isPasswordMatch.value
    }

    fun onRegister(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val hashedPassword = HashPassword.hashPassword(password.value)
                val user = User(
                    name = name.value,
                    lastName = lastName.value,
                    email = email.value,
                    hashedPassword = hashedPassword,
                    nationality = nationality.value
                )
                userRepository.addUser(user)
                onSuccess()
            } catch (e: Exception) {
                if (e.message == "HTTP 409 ") {
                    errorMessage.value = "El usuario ya está registrado"
                } else {
                    errorMessage.value = e.message ?: "Error al registrarse"
                }
                onError(errorMessage.value!!)
            }
        }
    }

}

