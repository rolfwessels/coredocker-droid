package com.coredocker.android.business.model


class User(
    /**
     * The id of the user.
     */
    val id: String? = null,
    /**
     * The name of the user.
     */
    val name: String? = null,
    /**
     * The email of the user.
     */
    val email: String? = null,
    /**
     * The roles of the user.
     */
    val roles: List<String>? = null,

    val password: String? = null
)