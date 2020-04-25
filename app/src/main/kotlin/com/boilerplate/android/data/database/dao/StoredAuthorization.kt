package com.boilerplate.android.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.boilerplate.android.business.model.Authorization

@Dao
interface StoredAuthorization : BaseDao<Authorization> {

    @Query("SELECT * FROM Authorization")
    fun getAll(): LiveData<List<Authorization>>

    @Query("SELECT * FROM Authorization")
    fun activeSession(): Authorization?

}