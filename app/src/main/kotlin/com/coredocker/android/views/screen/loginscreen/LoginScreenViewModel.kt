package com.coredocker.android.views.screen.loginscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coredocker.android.data.repository.AuthenticationRepository
import com.coredocker.android.services.Navigate
import com.coredocker.android.services.SnackBarNotification
import com.coredocker.android.util.LoadingState
import com.coredocker.android.util.extensions.default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.security.auth.login.LoginException

class LoginScreenViewModel (
    private val dataRepository: AuthenticationRepository,
    private val navigate: Navigate,
    private val snackBarNotification: SnackBarNotification
) : ViewModel() {
    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val pageState: MutableLiveData<LoadingState> = MutableLiveData<LoadingState>().default(LoadingState(false))

    fun onLoginClick() = GlobalScope.launch(IO) {
        var loadingState = validateState()
        pageState.postValue(loadingState)

        if (loadingState.isValid) {
                loadingState = LoadingState(false)
                try {
                    dataRepository.login(email.value!!, password.value!!)
                    navigate.toMainView()
                } catch (e: LoginException) {
                    loadingState =
                        LoadingState(false, mapOf(Pair("password", e.message!!)))
                } catch (e: Throwable) {
                    Timber.e("Error logging in ${e.message}")
                    snackBarNotification.push("Failed to log in. Please try again later.")
                }
                pageState.postValue(loadingState)

        }
    }

    private fun validateState(): LoadingState {
        val errors = mutableMapOf<String, String>()
        if (!email.value!!.contains("@")) errors["email"] = "Please enter valid email address."
        if (password.value!!.length < 2) errors["password"] = "Please enter valid password."
        return LoadingState(errors.isEmpty(), errors)
    }


}