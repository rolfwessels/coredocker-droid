package com.coredocker.android.data.network.category

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface CoreDockerApi {
    @GET("api/ping")
    suspend fun ping(): Response<PingResponse>

    @FormUrlEncoded
    @POST("connect/token")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("client_id") client_id: String = "coredocker.api",
        @Field("client_secret") client_secret: String = "super_secure_password",
        @Field("grant_type") grant_type: String = "password",
        @Field("scope") scope: String = "api"
    ): Response<LoginResponse>
}