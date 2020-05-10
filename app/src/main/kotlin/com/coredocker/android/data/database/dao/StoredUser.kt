package com.coredocker.android.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.coredocker.android.business.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface StoredUser : IBaseDao<User> {

    @Query("SELECT * FROM User")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM User")
    fun steamAll(): Flow<List<User>>

    @Query("UPDATE User SET isSynced = 'false'")
    suspend fun markAllAsUnSynced(): Int

    @Query("DELETE FROM User WHERE isSynced = 'false'")
    suspend fun removeUnSynced(): Int

    @Query("SELECT * FROM User WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): User?

    @Query("DELETE FROM User WHERE id = :id")
    suspend fun removeById(id: String): Int
}