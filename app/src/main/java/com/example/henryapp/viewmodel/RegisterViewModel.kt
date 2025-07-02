package com.example.henryapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.henryapp.model.data.entity.User
import com.example.henryapp.model.data.dao.UserDao
import com.example.henryapp.utils.HashPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userDao: UserDao
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
                val existingUser = userDao.getUserByEmail(email.value)
                if (existingUser != null) {
                    onError("El correo ya está registrado. Por favor, usa otro.")
                    return@launch
                }

                val hashedPassword = HashPassword.hashPassword(password.value)
                val user = User(
                    name = name.value,
                    lastName = lastName.value,
                    email = email.value,
                    hashedPassword = hashedPassword,
                    nationality = nationality.value
                )
                userDao.insertUser(user)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Error al registrarse")
            }
        }
    }
}

