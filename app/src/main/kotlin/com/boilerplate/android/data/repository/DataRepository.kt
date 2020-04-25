package com.boilerplate.android.data.repository

import com.auth0.android.jwt.JWT
import com.boilerplate.android.business.model.Authorization
import com.boilerplate.android.data.database.dao.StoredAuthorization
import com.boilerplate.android.data.network.category.CoreDockerApi
import com.boilerplate.android.data.network.category.LoginResponse
import java.util.*
import javax.security.auth.login.LoginException

class DataRepository(
    private val authorizations: StoredAuthorization,
    private val coreDockerApi: CoreDockerApi
) {
    private var tokenWithDetails: Authorization? = null

    @Throws(LoginException::class)
    suspend fun login(email: String, password: String): LoginResponse {
        val response = coreDockerApi.login(
            email,
            password
        )

        if (response.isSuccessful) {
            response.body()?.let {
                val buildTokenDetails = buildTokenDetails(it.access_token!!)
                authorizations.save(buildTokenDetails)
                this.tokenWithDetails = buildTokenDetails
                return it
            }
        }
        throw LoginException("Invalid username and password.")
    }

    private fun buildTokenDetails(token: String): Authorization {
        val jwt = JWT(token)
        return Authorization(1,
            token,
            jwt.getClaim("id").asString() ?: "",
            jwt.getClaim("given_name").asString() ?: "",
            jwt.getClaim("email").asString() ?: "",
            jwt.getClaim("name").asString() ?: "",
            jwt.getClaim("exp").asLong() ?: 0,
            jwt.getClaim("role").asList(String::class.java)
        )
    }

    fun isLoggedIn(): Boolean {
        println("----------------")
        val authorization = authorizations.activeSession()
        if (authorization != null) {
            return !authorization.isExpired()
        }
        return false

    }


}
