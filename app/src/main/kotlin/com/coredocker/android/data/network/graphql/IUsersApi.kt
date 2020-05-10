package com.coredocker.android.data.network.graphql

import com.coredocker.fragment.CommandResultDto
import com.coredocker.fragment.RealTimeNotificationsMessageFragment
import com.coredocker.fragment.UserDto
import kotlinx.coroutines.flow.Flow

interface IUsersApi {
    suspend fun allUsers(): PagedFragment<UserDto>

    suspend fun getUserById(id: String): UserDto

    suspend fun addUser(
        name: String,
        email: String,
        password: String?,
        roles: List<String> = listOf("Guest")
    ): CommandResultDto

    suspend fun updateUser(
        id: String,
        name: String,
        email: String,
        password: String?,
        roles: List<String>
    ): CommandResultDto

    suspend fun removeUser(id: String): CommandResultDto
    fun onDefaultEventSubscription(): Flow<RealTimeNotificationsMessageFragment>
}