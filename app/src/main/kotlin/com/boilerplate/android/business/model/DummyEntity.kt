package com.boilerplate.android.business.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DummyEntity(
    @PrimaryKey
    val id: Int,
    val value: String,
    val list: List<String>
)