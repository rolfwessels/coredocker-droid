package com.boilerplate.android.base.feature.loginscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.boilerplate.android.data.repository.DataRepository
import com.boilerplate.android.util.LoadingState
import com.boilerplate.android.util.extensions.default
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.security.auth.login.LoginException


class LoginScreenViewModel(
    private val dataRepository: DataRepository
) : ViewModel() {
    val isLoggedIn: MutableLiveData<Boolean> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val pageState: MutableLiveData<LoadingState> =
        MutableLiveData<LoadingState>().default(LoadingState(false))

    fun onLoginClick() {
        var loadingState = validateState()
        pageState.postValue(loadingState)

        if (loadingState.isValid) {
            GlobalScope.launch {
                loadingState = LoadingState(false)
                try {
                    dataRepository.login(email.value!!, password.value!!)
                    isLoggedIn.postValue(true)
                } catch (e: LoginException) {
                    loadingState =
                        LoadingState(false, mapOf<String, String>(Pair("password", e.message!!)))
                } catch (e: Exception) {
                    loadingState = LoadingState(
                        false,
                        mapOf<String, String>(
                            Pair(
                                "password",
                                "Unable to connect. Please try again later."
                            )
                        )
                    )
                }
                pageState.postValue(loadingState)
            }
        }
    }

    private fun validateState(): LoadingState {
        val errors = mutableMapOf<String, String>()
        if (!email.value!!.contains("@")) errors["email"] = "Please enter valid email address."
        if (password.value!!.length < 2) errors["password"] = "Please enter valid password."
        return LoadingState(errors.isEmpty(), errors)
    }


}