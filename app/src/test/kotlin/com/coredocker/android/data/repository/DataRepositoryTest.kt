package com.coredocker.android.data.repository

import com.coredocker.android.business.model.User
import com.coredocker.android.data.database.dao.StoredUser
import com.coredocker.android.data.network.graphql.IUsersApi
import com.coredocker.android.data.network.graphql.PagedFragment
import com.coredocker.android.util.Randomizer
import com.coredocker.android.util.extensions.anyObject
import com.coredocker.android.util.extensions.capture
import com.coredocker.android.util.extensions.safeCapture
import com.coredocker.fragment.CommandResultDto
import com.coredocker.fragment.UserDto
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class DataRepositoryTest {

    @Mock
    private lateinit var mockStoredUser: StoredUser
    @Mock
    private lateinit var mockUserApi: IUsersApi
    private lateinit var dataRepository: DataRepository

    @Captor
    private var captorUserList: ArgumentCaptor<List<User>>? = null
    @Captor
    private var captorUser: ArgumentCaptor<User>? = null

    @Before
    fun createDb() {
        dataRepository = DataRepository(mockUserApi, mockStoredUser)
    }

    @Test
    fun addUser_givenUserData_shouldCallAddUserOnApi() = runBlocking {
        // arrange
        val id = "id1"
        val name = "name"
        val email = "email"
        val roles = listOf("test")
        val password = "password"

        `when`(mockUserApi.addUser(name, email, password, roles))
            .thenReturn(buildCommandResult(id))

        // action
        val result = dataRepository.addUser(name, email, password, roles)
        // assert
        result shouldBeEqualTo id
        return@runBlocking
    }

    @Test
    fun addUser_givenUserData_shouldCallStoreLocalCopyInDb() = runBlocking {
        // arrange
        val id = "id1"
        val name = "name"
        val email = "email"
        val roles = listOf("test")
        val password = "password"

        `when`(mockUserApi.addUser(name, email, password, roles))
            .thenReturn(buildCommandResult(id))
        `when`(mockStoredUser.insert(anyObject<User>()))
            .thenReturn(Unit)
        // action
        dataRepository.addUser(name, email, password, roles)
        // assert
        verify(mockStoredUser, times(1)).insert(capture(captorUser))
        captorUser!!.value.email shouldBeEqualTo email
        return@runBlocking
    }

    @Test
    fun updateUser_givenUserData_shouldCallUpdateUserOnApi() = runBlocking {
        // arrange
        val name = "name"
        val email = "email"
        val roles = listOf("test")
        val password = "password"
        val id = "id1"
        `when`(mockUserApi.updateUser(id, name, email, password, roles))
            .thenReturn(buildCommandResult(id))
        `when`(mockStoredUser.getById(id))
            .thenReturn(User(id))
        `when`(mockStoredUser.save(anyObject<User>()))
            .thenReturn(Unit)
        // action
        val result = dataRepository.updateUser(id, name, email, password, roles)
        // assert
        verify(mockStoredUser, times(1)).getById(id)
        verify(mockStoredUser, times(1)).save(capture(captorUser))
        result shouldBeEqualTo id
        return@runBlocking
    }

    @Test
    fun removeUser_givenUserId_shouldCallRemoveUserOnApi() = runBlocking {
        // arrange
        val id = "id1"
        `when`(mockUserApi.removeUser(id))
            .thenReturn(buildCommandResult(id))
        // action
        val result = dataRepository.removeUser(id)
        // assert
        verify(mockStoredUser, times(1)).removeById(id)
        result shouldBeEqualTo id
        return@runBlocking
    }

    @Test
    fun upsertAllUsers_whenCalled_shouldLoadSaveDataToStorage() = runBlocking {
        // arrange
        val items = buildPagedItems()
        upsertAllUsersDefaultSetup(items)
        `when`(mockStoredUser.save(anyList())).thenReturn(Unit)
        // action
        dataRepository.upsertAllUsers()
        // assert
        verify(mockStoredUser, times(1)).save(captorUserList.safeCapture())
        captorUserList!!.value[0].id shouldBeEqualTo items.items[0].id
        captorUserList!!.value.size shouldBeEqualTo 1
        return@runBlocking
    }

    @Test
    fun upsertAllUsers_whenCalled_shouldReturnListOfUsers() = runBlocking {
        // arrange
        val items = buildPagedItems()
        upsertAllUsersDefaultSetup(items)
        // action
        val upsertAllUsers = dataRepository.upsertAllUsers()
        // assert
        upsertAllUsers.size shouldBeEqualTo items.items.size
        return@runBlocking
    }

    @Test
    fun upsertAllUsers_whenCalled_shouldMarkItemsAsRemovedThenAddThemAgain() = runBlocking {
        // arrange
        val items = buildPagedItems()
        upsertAllUsersDefaultSetup(items)
        // action
        dataRepository.upsertAllUsers()
        // assert
        verify(mockStoredUser, times(1)).markAllAsUnSynced()
        verify(mockStoredUser, times(1)).removeUnSynced()
        return@runBlocking
    }

    private suspend fun upsertAllUsersDefaultSetup(items: PagedFragment<UserDto>) {
        `when`(mockUserApi.allUsers())
            .thenReturn(items)
        `when`(mockStoredUser.markAllAsUnSynced())
            .thenReturn(1)
        `when`(mockStoredUser.removeUnSynced())
            .thenReturn(1)
    }

    private fun buildPagedItems(): PagedFragment<UserDto> {
        return PagedFragment(listOf(Randomizer.instance.nextObject(UserDto::class.java)), 1)
    }

    private fun buildCommandResult(id: String) =
        CommandResultDto(id = id, correlationId = "C", createdAt = Date())
}
