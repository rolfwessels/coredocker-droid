package com.coredocker.android.data.network.graphql

import com.apollographql.apollo.ApolloClient
import com.coredocker.android.data.network.buildGraphQlUrl
import com.coredocker.android.data.network.category.LoginResponse
import com.coredocker.android.data.network.httpClientWithAuthHeader
import com.coredocker.android.data.network.provideApolloClientFromHttpClient
import com.coredocker.android.data.network.provideCoreDockerApiService
import com.coredocker.android.data.network.provideDefaultOkHttpClient
import com.coredocker.android.data.network.provideRetrofit
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiIntegrationTestHelper {

    val retrofitClient: Retrofit by lazy {
        buildRetrofit(testUrl)
    }
    val testUrl = "https://api.dev.coredocker.wessels.online/"
    // val testUrl = "http://localhost:5000/"
    val loginResponse: LoginResponse? by lazy {
        runBlocking {
            loginResponse(testUrl)
        }
    }
    val apolloClient: ApolloClient by lazy { buildClient(testUrl, loginResponse) }
}

private fun buildRetrofit(url: String): Retrofit {
    val client = provideDefaultOkHttpClient()
    return Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()
}

private suspend fun loginResponse(url: String): LoginResponse? {
    println("Connecting to :$url")
    return provideCoreDockerApiService(
        provideRetrofit(
            provideDefaultOkHttpClient(),
            url
        )
    ).login(
        "admin@admin.com",
        "admin!"
    ).body()
}

private fun buildClient(url: String, user: LoginResponse?): ApolloClient {
    val graphQlUrl = buildGraphQlUrl(url)
    println(
        "Connecting to :$graphQlUrl with token ${user?.access_token?.substring(
            0,
            10
        )}..."
    )
    val okHttpClient =
        httpClientWithAuthHeader(graphQlUrl) { -> user?.access_token }
    return provideApolloClientFromHttpClient(
        graphQlUrl,
        okHttpClient
    )
}
