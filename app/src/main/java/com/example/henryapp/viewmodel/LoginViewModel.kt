package com.example.henryapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
) : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> get() = _userEmail

    private val _errorEvents = MutableSharedFlow<String>()
    val errorEvents = _errorEvents.asSharedFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val success = loginRepository.login(email, password)
                _loginResult.value = success
                if (success) {
                    _userEmail.value = email
                } else {
                    _errorEvents.emit("Credenciales inválidas. Inténtalo de nuevo.")
                }
            } catch (e: Exception) {
                if (e.message == "HTTP 404 "){
                    _errorEvents.emit("Usuario no encontrado. Por favor, regístrate.")
                } else if (e.message == "HTTP 401 "){
                    _errorEvents.emit("Contraseña incorrecta. Por favor, inténtalo de nuevo.")
                }else{
                    _errorEvents.emit( "Ocurrió un error al iniciar sesión.")
                }
                _loginResult.value = false

            }
        }
    }
}
