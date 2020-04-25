package com.boilerplate.android.network.category


import com.boilerplate.android.data.network.NetworkConstants
import com.boilerplate.android.data.network.category.CoreDockerApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@ExperimentalCoroutinesApi
class CoreDockerApiTest  {
    private lateinit var apiService: CoreDockerApi
    @Before
    fun setup() {
        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        apiService = Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create(CoreDockerApi::class.java)
    }


    @Test
    fun ping_givenValidCall_shouldReturnASuccessfulResult() {
        // prepare
        // act
        val pingResult = runBlocking { apiService.ping() }

        // assert
        assertThat(pingResult.isSuccessful,equalTo(true))
        assertThat(pingResult.body()?.environment,equalTo("Production"))
    }

    @Test
    fun login_givenValidUserNameAndPassword_shouldCreateValidToken() {
        // prepare
        // act
        val pingResult = runBlocking { apiService.login(
            "admin@admin.com",
            "admin!"
        )
        }

        // assert
        assertThat(pingResult.isSuccessful,equalTo(true))
        assertThat(pingResult.body()?.access_token?.length,equalTo(43))
    }

    @Test
    fun login_givenInvalidUserNameAndPassword_shouldFail() {
        // prepare
        // act
        val pingResult = runBlocking {
            apiService.login(
                "admin@admin.com",
                "admin-nope"
            )
        }

        // assert
        assertThat(pingResult.isSuccessful, equalTo(false))
        assertThat(pingResult.body(), `is`(nullValue()))
    }
}