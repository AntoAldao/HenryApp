package com.example.henryapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
) : ViewModel() {


    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> get() = _userEmail

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val success = loginRepository.login(email, password)
                _loginResult.value = success
                if (success) {
                    _userEmail.value = email // Guarda el email si el login es exitoso
                } else {
                    _errorMessage.value = "Credenciales inválidas. Inténtalo de nuevo."
                }
            } catch (e: Exception) {
                _loginResult.value = false
                _errorMessage.value = e.message ?: "Ocurrió un error al iniciar sesión."
            }
        }
    }
}
