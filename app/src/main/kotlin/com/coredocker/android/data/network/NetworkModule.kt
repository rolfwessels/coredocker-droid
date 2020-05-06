package com.coredocker.android.data.network

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.subscription.WebSocketSubscriptionTransport
import com.coredocker.android.data.network.category.CoreDockerApi
import com.coredocker.android.data.network.graphql.UsersApi
import com.coredocker.android.data.repository.AuthenticationRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.URL
import java.util.concurrent.TimeUnit


private val TAG: String = "networkModule"
val networkModule = module {
    single {
        provideDefaultOkHttpClient()
    }
    single {
        provideRetrofit(
            get(),
            NetworkConstants.BASE_URL
        )
    }
    single { provideCoreDockerApiService(get()) }
    single {
        provideApolloClient(
            buildGraphQlUrl(NetworkConstants.BASE_URL),
            get()
        )
    }
    single { provideCoreUserService(get()) }
}

fun buildGraphQlUrl(baseUrl: String) = URL(baseUrl).toURI().resolve("graphql").toString()

fun provideDefaultOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().build()
}

fun provideRetrofit(client: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .client(client)
        .baseUrl(url)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}
fun provideApolloClient(url :String,authenticationRepository: AuthenticationRepository ): ApolloClient {
    val client = httpClientWithAuthHeader(
        url,
        getToken = authenticationRepository::currentToken
    )
    return provideApolloClientFromHttpClient(
        url,
        client
    )
}

fun provideApolloClientFromHttpClient(
    url: String,
    client: OkHttpClient
): ApolloClient {
    return ApolloClient.builder()
        .serverUrl(url)
        .okHttpClient(client)
        .subscriptionTransportFactory(WebSocketSubscriptionTransport.Factory(url, client))
        .build()
}

fun httpClientWithAuthHeader(
    url: String,
    getToken : () -> String?
): OkHttpClient {
    return OkHttpClient.Builder()
        .readTimeout(30,TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val currentToken = getToken()
            Log.i(TAG, "Request to $url with token: '${currentToken?.substring(0, 10)}...'")
            if (currentToken != null) {
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header(
                        "Authorization",
                        "Bearer $currentToken"
                    ) // <-- this is the important line
                val request = requestBuilder.build()
                chain.proceed(request)
            } else {
                chain.proceed(chain.request())
            }
        }.build()
}

private fun LoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
}

fun provideCoreDockerApiService(retrofit: Retrofit): CoreDockerApi =
    retrofit.create(CoreDockerApi::class.java)

fun provideCoreUserService(apolloClient: ApolloClient): UsersApi  {
    return UsersApi(apolloClient)
}