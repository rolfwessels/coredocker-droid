package com.coredocker.android.data.repository

import com.coredocker.android.business.model.User
import com.coredocker.android.business.model.mapToUser
import com.coredocker.android.data.database.dao.StoredUser
import com.coredocker.android.data.network.graphql.IUsersApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.Closeable

class DataRepository(
    private val usersApi: IUsersApi,
    private val storedUser: StoredUser
) : Closeable {

    private var isStarted: Boolean = false
    private val dataSyncScope = CoroutineScope(Job() + Dispatchers.Default)

    fun allUsers(): Flow<List<User>> = flow {
        val all = storedUser.steamAll()
        startMonitoring()
        emitAll(all)
    }

    private fun startMonitoring() {
        if (isStarted) {
            Timber.d("Monitoring already running")
            return
        }
        dataSyncScope.launch {
            try {
                Timber.i("---------------------------")
                Timber.i("monitoring started.")
                Timber.i("---------------------------")
                isStarted = true
                upsertAllUsers()
                usersApi.onDefaultEventSubscription().collect {
                    if (it.event.startsWith("User")) {
                        upsertAllUsers()
                    }
                }
            } catch (e: Throwable) {
                Timber.e("Monitoring stopped: ${e.message}")
            } finally {
                isStarted = false

                Timber.w("---------------------------")
                Timber.w("monitoring ending.")
                Timber.w("---------------------------")
            }
        }
    }

    suspend fun upsertAllUsers(): List<User> = withContext(IO) {
        Timber.d("Query all users")
        val queryAll = usersApi.allUsers()
        val updateUnSynced = storedUser.markAllAsUnSynced()
        Timber.d("Marked users as unsynced $updateUnSynced")
        val users = queryAll.items.map { it.mapToUser() }.toList()
        storedUser.save(users)
        val removeUnSynced = storedUser.removeUnSynced()
        Timber.d("Removed $removeUnSynced items because they were not synced.")
        return@withContext users
    }

    suspend fun addUser(
        name: String,
        email: String,
        password: String,
        roles: List<String> = listOf("Guest")
    ): String? {
        Timber.i("Adding new user [$name]")
        storedUser.insert(User("_$email", name, email, roles))
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
        val found = storedUser.getById(id)
        if (found != null) {
            found.name = name
            found.email = email
            found.roles = roles
            storedUser.save(found)
        }
        val response = usersApi.updateUser(id, name, email, password, roles)
        return response.id
    }

    suspend fun removeUser(id: String): String? {
        Timber.i("Removing user [$id]")
        storedUser.removeById(id)
        val response = usersApi.removeUser(id)
        return response.id
    }

    override fun close() {
        dataSyncScope.cancel()
    }
}
