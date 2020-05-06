package com.coredocker.android.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.coredocker.android.business.model.Authorization

@Dao
interface StoredAuthorization : BaseDao<Authorization> {

    @Query("SELECT * FROM Authorization")
    fun getAll(): LiveData<List<Authorization>>

    @Query("SELECT * FROM Authorization")
    fun activeSession(): Authorization?
}