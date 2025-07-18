package com.example.core.repository

import com.example.core.model.data.entity.Credentials
import com.example.core.model.data.entity.LoginResponse
import com.example.core.model.data.entity.User
import com.example.core.model.data.remote.ApiService
import com.example.core.model.repository.LoginRepository
import com.example.core.utils.HashPassword
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class LoginRepositoryTest {

    private lateinit var apiService: ApiService
    private lateinit var loginRepository: LoginRepository

    @Before
    fun setup() {
        apiService = mock(ApiService::class.java)
        loginRepository = LoginRepository(apiService)
    }

    @Test
    fun `login returns true for valid credentials`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        val hashedPassword = HashPassword.hashPassword(password)
        val user = LoginResponse(
            message = "Login exitoso",
            user = User(
                id = 1,
                name = "Test",
                lastName = "User",
                email = email,
                hashedPassword = hashedPassword,
                nationality = "Testland",
                imageUrl = "http://example.com/image.jpg"
            )
        )

        val credentials = Credentials(
            email = email,
            hashedPassword= hashedPassword
        )
        `when`(apiService.login(credentials)).thenReturn(user)

        val result = loginRepository.login(email, password)

        assertTrue(result)
    }

    @Test
    fun `login returns false when user does not exist`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        val credentials = Credentials(
            email = email,
            hashedPassword = password
        )

        `when`(apiService.login(credentials)).thenReturn(null)

        val result = loginRepository.login(email, password)

        assertFalse(result)
    }

    @Test
    fun `login returns false when password is incorrect`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        val credentials = Credentials(
            email = email,
            hashedPassword = password
        )

        `when`(apiService.login(credentials)).thenReturn(null)

        val result = loginRepository.login(email, password)

        assertFalse(result)
    }
}