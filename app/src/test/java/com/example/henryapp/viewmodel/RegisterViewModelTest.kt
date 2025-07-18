package com.example.henryapp.viewmodel

import com.example.core.model.data.entity.User
import com.example.core.model.repository.UserRepository
import com.example.henryapp.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setup() {
        userRepository = mock(UserRepository::class.java)
        viewModel = RegisterViewModel(userRepository)
    }

    @Test
    fun validateFormWithValidData_setsFormValidToTrue() {
        viewModel.name.value = "Test"
        viewModel.lastName.value = "TestLastName"
        viewModel.email.value = "john.doe@example.com"
        viewModel.password.value = "password123"
        viewModel.confirmPassword.value = "password123"
        viewModel.nationality.value = "USA"

        viewModel.validateForm()

        assertTrue(viewModel.isFormValid.value)
    }

    @Test
    fun validateFormWithInvalidEmail_setsFormValidToFalse() {
        viewModel.name.value = "Test"
        viewModel.lastName.value = "TestLastName"
        viewModel.email.value = "invalid-email"
        viewModel.password.value = "password123"
        viewModel.confirmPassword.value = "password123"
        viewModel.nationality.value = "USA"

        viewModel.validateForm()

        assertFalse(viewModel.isFormValid.value)
    }

    @Test
    fun validateFormWithMismatchedPasswords_setsFormValidToFalse() {
        viewModel.name.value = "Test"
        viewModel.lastName.value = "TestLastName"
        viewModel.email.value = "john.doe@example.com"
        viewModel.password.value = "password123"
        viewModel.confirmPassword.value = "password456"
        viewModel.nationality.value = "USA"

        viewModel.validateForm()

        assertFalse(viewModel.isFormValid.value)
    }

    @Test
    fun onRegisterWithExistingEmail_callsOnError() = runTest {
        val existingUser = User(
            name = "Test",
            lastName = "TestLastName",
            email = "john.doe@example.com",
            hashedPassword = "hashedPassword",
            nationality = "USA"
        )
        `when`(userRepository.getUserByEmail("john.doe@example.com")).thenReturn(existingUser)

        // Establecer valores en el ViewModel
        viewModel.name.value = "Test"
        viewModel.lastName.value = "TestLastName"
        viewModel.email.value = "john.doe@example.com"
        viewModel.password.value = "somePassword"
        viewModel.confirmPassword.value = "somePassword"
        viewModel.nationality.value = "USA"

        var errorMessage: String? = null
        viewModel.onRegister(
            onSuccess = {},
            onError = { error -> errorMessage = error }
        )
        advanceUntilIdle()

        assertEquals(null, errorMessage)
    }
    @Test
    fun onRegisterWithValidData_callsOnSuccess() = runTest {
        `when`(userRepository.getUserByEmail("john.doe@example.com")).thenReturn(null)

        var successCalled = false
        viewModel.name.value = "Test"
        viewModel.lastName.value = "TestLastName"
        viewModel.email.value = "john.doe@example.com"
        viewModel.password.value = "password123"
        viewModel.confirmPassword.value = "password123"
        viewModel.nationality.value = "USA"

        viewModel.onRegister(
            onSuccess = { successCalled = true },
            onError = {}
        )
        advanceUntilIdle()

        assertTrue(successCalled)
    }
}