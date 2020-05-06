package com.coredocker.android.data.repository

import android.util.Log
import com.coredocker.android.data.network.graphql.PagedFragment
import com.coredocker.android.data.network.graphql.UsersApi
import com.coredocker.fragment.UserFragment
import kotlinx.coroutines.flow.*

private val TAG: String = DataRepository::class.java.simpleName

class DataRepository(
    private val usersApi: UsersApi
) {
    fun allUsers(): Flow<PagedFragment<UserFragment>> = flow {
        val queryAll = usersApi.queryAll()
        emit(queryAll)
        emitAll(usersApi.onDefaultEventSubscription()
            .filter { it.event.startsWith("User") }
            .onEach { Log.i(TAG, "Event ${it.event}") }
            .map { usersApi.queryAll() })
    }

    suspend fun addUser(name : String, email: String, password: String, roles : List<String> = listOf("Guest")): String? {
        val response = usersApi.addUser(name,email, password, roles)
        return response.id
    }

    suspend fun updateUser(id: String, name: String, email: String, password: String?, roles: List<String>): String? {
        val response = usersApi.updateUser(id ,name,email, password, roles)
        return response.id
    }

    suspend fun userRemove(id: String) : String? {
        val response = usersApi.removeUser(id)
        return response.id
    }
}
