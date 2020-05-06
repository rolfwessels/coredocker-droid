package com.coredocker.android.network.category


import com.coredocker.android.data.network.category.CoreDockerApi
import com.coredocker.android.data.network.graphql.ApiIntegrationTestHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class CoreDockerApiTest {
    private lateinit var apiService: CoreDockerApi

    @Before
    fun setup() {

        apiService = ApiIntegrationTestHelper.retrofitClient
            .create(CoreDockerApi::class.java)
    }


    @Test
    fun ping_givenValidCall_shouldReturnASuccessfulResult() {

        // act
        val pingResult = runBlocking { apiService.ping() }
        // assert
        assertThat(pingResult.isSuccessful, equalTo(true))
        assertThat(
            pingResult.body()?.environment!!.replace("development", "Production"),
            equalTo("Production")
        )
    }

    @Test
    fun login_givenValidUserNameAndPassword_shouldCreateValidToken() {
        // prepare
        // act
        val pingResult = runBlocking {
            apiService.login(
                "admin@admin.com",
                "admin!"
            )
        }
        // assert
        assertThat(pingResult.isSuccessful, equalTo(true))
        assertThat(pingResult.body()?.access_token?.length!!, greaterThan(43))
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