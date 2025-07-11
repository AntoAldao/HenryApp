
import android.content.Context
import com.example.core.model.data.entity.User
import com.example.core.model.repository.UserRepository
import com.example.henryapp.utils.MainDispatcherRule
import com.example.henryapp.viewmodel.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setup() {
        userRepository = mock(UserRepository::class.java)
        viewModel = ProfileViewModel(userRepository)
    }

    @Test
    fun loadUserUpdatesUserState() = runTest {
        val user = User(
            id = 1,
            name = "Nombre",
            lastName = "Apellido",
            email = "test@example.com",
            hashedPassword = "hashedPassword",
            nationality = "Nacionalidad",
            imageUrl = "url"
        )

        `when`(userRepository.getUserByEmail("test@example.com")).thenReturn(user)

        viewModel.loadUser("test@example.com")
        advanceUntilIdle()
        assertEquals(user, viewModel.user.value)
    }

    @Test
    fun updateUserWithoutImage_updatesUserAndCallsOnSuccess() = runTest {
        val user = User(
            id = 1,
            name = "Nombre",
            lastName = "Apellido",
            email = "test@example.com",
            hashedPassword = "hashedPassword",
            nationality = "Argentina",
            imageUrl = "url"
        )

        `when`(userRepository.getUserByEmail(user.email)).thenReturn(user)

        viewModel.loadUser(user.email)
        advanceUntilIdle()

        // Arrange
        val updatedName = "NuevoNombre"
        val updatedLastName = "NuevoApellido"
        val updatedNationality = "Chile"
        var successCalled = false

        `when`(userRepository.updateUser(user.copy(
            name = updatedName,
            lastName = updatedLastName,
            nationality = updatedNationality
        ))).then {}

        // Act
        viewModel.updateUser(
            context = mock(Context::class.java),
            name = updatedName,
            lastName = updatedLastName,
            nationality = updatedNationality,
            imageUri = null,
            onSuccess = { successCalled = true },
            onError = { error -> throw AssertionError("Error inesperado: $error") }
        )

        advanceUntilIdle()

        // Assert
        val updatedUser = viewModel.user.value!!
        assertEquals(updatedName, updatedUser.name)
        assertEquals(updatedLastName, updatedUser.lastName)
        assertEquals(updatedNationality, updatedUser.nationality)
        assertEquals(true, successCalled)
    }

}