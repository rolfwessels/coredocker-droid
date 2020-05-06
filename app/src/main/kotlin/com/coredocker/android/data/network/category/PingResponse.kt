package com.coredocker.android.data.network.category

data class PingResponse(
    val database: String,
    val environment: String,
    val machineName: String,
    val version: String
)