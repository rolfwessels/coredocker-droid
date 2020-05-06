package com.coredocker.android.data.network.graphql

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.coroutines.toFlow
import com.coredocker.*
import com.coredocker.fragment.CommandResultFragment
import com.coredocker.fragment.RealTimeNotificationsMessageFragment
import com.coredocker.fragment.UserFragment
import com.coredocker.type.UserCreateUpdateModelInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.math.BigInteger


class UsersApi(
    private val _apolloClient: ApolloClient
) {
    private val _tag: String = UsersApi::class.java.simpleName

    suspend fun queryAll(): PagedFragment<UserFragment> {

        val response =  _apolloClient.query(AllUsersQuery()).toDeferred().await()
        validateResponse(response, response.data?.users?.paged != null)
        return PagedFragment(
            response.data!!.users.paged.items.map { it -> it!!.fragments.userFragment },
            BigInteger(response.data!!.users.paged.count.toString())
        )
    }

    suspend fun addUser(
        name: String,
        email: String,
        password: String?,
        roles: List<String> = listOf("Guest")
    ): CommandResultFragment {

        val userCreateModel = UserCreateUpdateModelInput(
            email, name, Input.optional(password) ,
            roles
        )
        
        val response =  _apolloClient.mutate(UserAddMutation(userCreateModel)).toDeferred().await()
        validateResponse(response, response.data?.users?.create != null)
        return response.data?.users?.create?.fragments!!.commandResultFragment
    }

    suspend fun updateUser(id: String, name: String, email: String, password: String?, roles: List<String>): CommandResultFragment {
        val userCreateModel = UserCreateUpdateModelInput(
            email, name, Input.optional(password),
            roles
        )
        val response =  _apolloClient.mutate(UserUpdateMutation(id , userCreateModel)).toDeferred().await()
        validateResponse(response, response.data?.users?.update != null)
        return response.data?.users?.update?.fragments!!.commandResultFragment
    }

    suspend fun removeUser(id: String): CommandResultFragment {
        val response =  _apolloClient.mutate(UserRemoveMutation(id)).toDeferred().await()
        validateResponse(response, response.data?.users?.remove != null)
        return response.data?.users?.remove?.fragments!!.commandResultFragment
    }

    fun onDefaultEventSubscription(): Flow<RealTimeNotificationsMessageFragment> {
        Timber.i("Subscribe to onDefaultEventSubscription")
        return _apolloClient.subscribe(OnDefaultEventSubscription())
            .toFlow()
            .filter { it.data != null }
            .map { it.data!!.onDefaultEvent.fragments.realTimeNotificationsMessageFragment }
    }

    private fun <T>validateResponse(
        response: Response<T>,
        hasData: Boolean
    ) {
        if (!hasData) {
            response.errors?.forEach {
                Timber.e("Error: ${it.message}")
                throw GraphQlException(it.message)
            }

        } else if (response.errors?.any() == true) {
            response.errors!!.forEach {
                Timber.w("Warning: ${it.message}")
            }
        }
    }




}

