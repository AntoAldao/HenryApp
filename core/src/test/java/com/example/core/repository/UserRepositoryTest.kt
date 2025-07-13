// core/src/test/java/com/example/core/repository/UserRepositoryTest.kt
package com.example.core.repository

import android.content.Context
import android.net.Uri
import com.example.core.model.data.dao.UserDao
import com.example.core.model.data.entity.User
import com.example.core.model.repository.UserRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class UserRepositoryTest {

    private lateinit var userDao: UserDao
    private lateinit var  userRepository: UserRepository
    private lateinit var mockContext: Context
    private lateinit var mockUri: Uri

    @Before
    fun setup() {
        userDao = mock(UserDao::class.java)
        userRepository = UserRepository(userDao)
        mockContext = mock(Context::class.java)
        mockUri = mock(Uri::class.java)
    }

    @Test
    fun `getUserByEmail With Existing Email returns User`() = runTest {

        val expectedUser = User(
            id = 1,
            name = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            hashedPassword = "hashedPassword",
            nationality = "USA",
            imageUrl = "url"
        )
        `when`(userDao.getUserByEmail("john.doe@example.com")).thenReturn(expectedUser)

        val result = userRepository.getUserByEmail("john.doe@example.com")

        assertEquals(expectedUser, result)
    }

    @Test
    fun `getUserByEmail With Non Existing Email returns Null`() = runTest {

        `when`(userDao.getUserByEmail("nonexistent@example.com")).thenReturn(null)

        val result = userRepository.getUserByEmail("nonexistent@example.com")

        assertEquals(null, result)
    }

}