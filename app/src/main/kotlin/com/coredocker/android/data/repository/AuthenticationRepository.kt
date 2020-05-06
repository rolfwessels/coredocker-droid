package com.coredocker.android.data.repository

import com.auth0.android.jwt.JWT
import com.coredocker.android.business.model.Authorization
import com.coredocker.android.data.database.dao.StoredAuthorization
import com.coredocker.android.data.network.category.CoreDockerApi
import com.coredocker.android.data.network.category.LoginResponse
import javax.security.auth.login.LoginException

class AuthenticationRepository(
    private val storedAuthorization: StoredAuthorization,
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
                storedAuthorization.save(buildTokenDetails)
                this.tokenWithDetails = buildTokenDetails
                return it
            }
        }
        throw LoginException("Invalid username and password.")
    }

    private fun buildTokenDetails(token: String): Authorization {
        val jwt = JWT(token)
        return Authorization(
            1,
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
        val authorization = authorizedUser()
        return authorization != null
    }

    fun currentToken(): String? {
        return authorizedUser()?.token
    }

    private fun authorizedUser(): Authorization? {
        if (tokenWithDetails == null) {
            tokenWithDetails = storedAuthorization.activeSession()
        }
        if (tokenWithDetails?.isExpired() == true) {
            return null
        }
        return tokenWithDetails
    }
}
