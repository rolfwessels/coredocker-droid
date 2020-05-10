package com.coredocker.android.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coredocker.android.business.model.Authorization
import com.coredocker.android.business.model.User
import com.coredocker.android.data.database.converter.RoomTypeConverter
import com.coredocker.android.data.database.dao.StoredAuthorization
import com.coredocker.android.data.database.dao.StoredUser

@Database(
    entities = [Authorization::class, User::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storedAuthorization(): StoredAuthorization
    abstract fun storedUser(): StoredUser
}