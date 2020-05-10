package com.coredocker.android.data.network.graphql

import com.coredocker.android.util.Randomizer
import com.coredocker.android.util.extensions.runOnBackground
import com.coredocker.android.util.extensions.shouldBeCloseTo
import com.coredocker.android.util.waitTill
import com.coredocker.fragment.CommandResultDto
import com.coredocker.fragment.RealTimeNotificationsMessageFragment
import com.coredocker.fragment.UserDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import java.util.Date

class UsersApiTest {

    private val queryAllResult: PagedFragment<UserDto> by lazy {
        runBlocking { usersApi.allUsers() }
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
        assertThat(result.count, Matchers.equalTo(result.items.size))
    }

    @Test
    fun crud_givenValidInput_shouldAddUpdateAndRemoveUser() = runBlocking {
        val user = Randomizer.instance.nextObject(UserDto::class.java)
        val password = user.id
        // act

        val resultAdd = usersApi.addUser(user.name, user.email, password)
        val resultUpdate = usersApi.updateUser(
            resultAdd.id!!,
            user.name + " update",
            user.email,
            password,
            listOf("guest")
        )
        val resultFindOne = usersApi.getUserById(resultAdd.id!!)
        val resultRemove = usersApi.removeUser(resultAdd.id!!)

        // assert
        resultAdd.id shouldBeEqualTo resultUpdate.id
        resultAdd.id shouldBeEqualTo resultRemove.id
        resultFindOne.name shouldBeEqualTo user.name + " update"

        resultFindOne.createDate.shouldBeCloseTo(Date(), 10000)
        resultFindOne.updateDate.shouldBeCloseTo(Date(), 10000)
        return@runBlocking
    }

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
            // try again for ci
            createRandomUser()
            mutableList.waitTill(10000) {
                it.size > 0
            }
        }
        assertThat(mutableList.size, Matchers.greaterThanOrEqualTo(1))
        assertThat(mutableList.map { it.event }, Matchers.contains("UserCreated"))
    }

    private suspend fun createRandomUser(): CommandResultDto {
        val user = Randomizer.instance.nextObject(UserDto::class.java)
        return usersApi.addUser(
            user.name, user.email, user.id
        )
    }
}
