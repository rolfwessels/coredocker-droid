package com.boilerplate.android.data.network.category

data class LoginResponse(
    val access_token: String? = null,
    val expires_in: Int = 0,
    val scope: String? = "",
    val token_type: String? = ""
)