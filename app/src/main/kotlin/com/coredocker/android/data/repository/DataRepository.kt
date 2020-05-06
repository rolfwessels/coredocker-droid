package com.coredocker.android.data.repository

import com.coredocker.android.data.network.graphql.PagedFragment
import com.coredocker.android.data.network.graphql.UsersApi
import com.coredocker.fragment.UserFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class DataRepository(
    private val usersApi: UsersApi
) {
    fun allUsers(): Flow<PagedFragment<UserFragment>> = flow {
        Timber.i("Start query flow for user")
        val queryAll = usersApi.queryAll()
        emit(queryAll)
        emitAll(usersApi.onDefaultEventSubscription()
            .filter { it.event.startsWith("User") }
            .onEach { Timber.i("Event found ${it.event}. Refreshing users.") }
            .map { usersApi.queryAll() })
    }

    suspend fun addUser(
        name: String,
        email: String,
        password: String,
        roles: List<String> = listOf("Guest")
    ): String? {
        Timber.i("Adding new user [$name]")
        val response = usersApi.addUser(name, email, password, roles)
        return response.id
    }

    suspend fun updateUser(
        id: String,
        name: String,
        email: String,
        password: String?,
        roles: List<String>
    ): String? {
        Timber.i("Updating new user [$id]")
        val response = usersApi.updateUser(id, name, email, password, roles)
        return response.id
    }

    suspend fun userRemove(id: String): String? {
        Timber.i("Removing user [$id]")
        val response = usersApi.removeUser(id)
        return response.id
    }
}
