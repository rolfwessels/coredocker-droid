package com.coredocker.android.data.network.graphql

import com.coredocker.android.util.buildRandomString
import com.coredocker.android.util.extensions.runOnBackground
import com.coredocker.android.util.waitTill
import com.coredocker.fragment.CommandResultFragment
import com.coredocker.fragment.RealTimeNotificationsMessageFragment
import com.coredocker.fragment.UserFragment
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test


class UsersApiTest {

    private val queryAllResult: PagedFragment<UserFragment> by lazy {
        runBlocking { usersApi.queryAll() }
    }
    private lateinit var usersApi: UsersApi


    @Before
    fun setUp() {
        usersApi = UsersApi(ApiIntegrationTestHelper.apolloClient)
    }

    @Test
    fun queryAll_givenCall_shouldReturnUsers() {
        // prepare
        // act
        val result = queryAllResult
        // assert
        assertThat(result.items.size, Matchers.greaterThan(0))
    }

    @Test
    fun queryAll_givenCall_shouldAllowAccessToUserInfo() {
        // act
        val result = queryAllResult
        // assert
        assertThat(result.items[0].email, Matchers.containsString("@"))
    }

    @Test
    fun queryAll_givenCall_shouldHaveCorrectCount() {
        // act
        val result = queryAllResult
        // assert
        assertThat(result.count, Matchers.equalTo(result.items.size.toBigInteger()))
    }


    @Test
    fun addUser_givenValidInput_shouldReturnAnId() = runBlocking {
        // act
        val result = createRandomUser()
        // assert
        assertThat(result.id, Matchers.not(Matchers.isEmptyOrNullString()))
    }

    @InternalCoroutinesApi
    @Test
    fun onDefaultEventSubscription_whenCalled_shouldSubscribe() = runBlocking {
        // act
        val onDefaultEventSubscription = usersApi.onDefaultEventSubscription()
        val mutableList = mutableListOf<RealTimeNotificationsMessageFragment>()
        runOnBackground {
            onDefaultEventSubscription.collect {
                mutableList.add(it)
            }
        }
        delay(200)
        createRandomUser()
        mutableList.waitTill {
            it.size > 0
        }
        if (mutableList.size == 0) {
            //try again for ci
            createRandomUser()
            mutableList.waitTill(10000) {
                it.size > 0
            }
        }
        assertThat(mutableList.size, Matchers.greaterThanOrEqualTo(1))
        assertThat(mutableList.map { it.event }, Matchers.contains("UserCreated"))

    }

    private suspend fun createRandomUser(): CommandResultFragment {
        return usersApi.addUser(
            "onDefaultEventSubscription", "${buildRandomString()}@asdf.com",
            buildRandomString()
        )
    }


}

