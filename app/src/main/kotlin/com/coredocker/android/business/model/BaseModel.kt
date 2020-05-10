package com.coredocker.android.business.model

import androidx.room.PrimaryKey

open class BaseModel(
    /**
     * The id of the user.
     */
    @PrimaryKey
    val id: String,
    /**
     * used to determine sync status
     */
    var isSynced: Boolean = true
)