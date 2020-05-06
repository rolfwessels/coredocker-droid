package com.coredocker.android.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coredocker.android.business.model.Authorization
import com.coredocker.android.data.database.converter.ListStringTypeConverter
import com.coredocker.android.data.database.dao.StoredAuthorization

@Database(
    entities = [Authorization::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListStringTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storedAuthorization(): StoredAuthorization
}