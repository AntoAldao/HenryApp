package com.example.core.repository

import com.example.core.model.data.dao.UserDao
import com.example.core.model.data.entity.User
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

    private lateinit var userDao: UserDao
    private lateinit var loginRepository: LoginRepository

    @Before
    fun setup() {
        userDao = mock(UserDao::class.java)
        loginRepository = LoginRepository(userDao)
    }

    @Test
    fun `login returns true for valid credentials`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        val hashedPassword = HashPassword.hashPassword(password)
        val user = User(
            id = 1,
            name = "Test",
            lastName = "User",
            email = email,
            hashedPassword = hashedPassword,
            nationality = "Testland",
            imageUrl = "http://example.com/image.jpg"
        )

        `when`(userDao.getUserByEmail(email)).thenReturn(user)

        val result = loginRepository.login(email, password)

        assertTrue(result)
    }

    @Test
    fun `login returns false when user does not exist`() = runTest {
        val email = "nonexistent@example.com"
        `when`(userDao.getUserByEmail(email)).thenReturn(null)

        val result = loginRepository.login(email, "anyPassword")

        assertFalse(result)
    }

    @Test
    fun `login returns false when password is incorrect`() = runTest {
        val email = "test@example.com"
        val correctPassword = "password123"
        val wrongPassword = "wrongpassword"
        val user = User(
            id = 1,
            name = "Test",
            lastName = "User",
            email = email,
            hashedPassword = HashPassword.hashPassword(correctPassword),
            nationality = "Testland",
            imageUrl = "http://example.com/image.jpg"
        )

        `when`(userDao.getUserByEmail(email)).thenReturn(user)

        val result = loginRepository.login(email, wrongPassword)

        assertFalse(result)
    }
}
