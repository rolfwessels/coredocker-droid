package com.coredocker.android.views.components.user

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coredocker.android.data.repository.DataRepository
import com.coredocker.android.services.Navigate
import com.coredocker.android.services.SnackBarNotification
import com.coredocker.android.util.LoadingState
import com.coredocker.android.util.extensions.default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class UserCreateUpdateFragmentViewModel(
    private val dataRepository: DataRepository,
    private val navigate: Navigate,
    private val notification: SnackBarNotification

) : ViewModel() {
    var id: String? = null
    val name: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val roles: MutableLiveData<List<String>> = MutableLiveData<List<String>>().default(
        listOf("Guest")
    )
    val pageState: MutableLiveData<LoadingState> = MutableLiveData<LoadingState>().default(
        LoadingState(false)
    )

    fun isChecked(role: String): Boolean {
        return roles.value!!.contains(role)
    }

    val isUpdate: Boolean
        get() {
            return !this.id.isNullOrEmpty()
        }

    fun switchRoles(role: String) {
        val mutateRoles = roles.value!!.toMutableList()
        if (mutateRoles.contains(role)) {
            mutateRoles.remove(role)
        } else {
            mutateRoles.add(role)
        }
        roles.postValue(mutateRoles)
    }

    fun onSaveClick() = GlobalScope.launch(IO) {
        var loadingState = validateState()
        pageState.postValue(loadingState)

        if (loadingState.isValid) {

            loadingState = LoadingState(false)
            try {
                callAddOrUpdateOnApi()
                navigate.toUserList()
                notification.push("User saved.", SnackBarNotification.SnackBarMessage.Types.Success)
            } catch (e: Throwable) {
                Timber.w("Failed to save user ${e.message}")
                notification.push(
                    e.message ?: "Failed to save user",
                    SnackBarNotification.SnackBarMessage.Types.Failure
                )
            }
            pageState.postValue(loadingState)
        }

    }

    private suspend fun callAddOrUpdateOnApi() {
        if (isUpdate) {
            dataRepository.updateUser(
                id!!,
                name.value!!,
                email.value!!,
                password.value,
                roles.value!!
            )
        } else {
            dataRepository.addUser(
                name.value!!,
                email.value!!,
                password.value!!,
                roles.value!!
            )
        }
    }

    private fun validateState(): LoadingState {
        val errors = mutableMapOf<String, String>()

        val user = this
        if (user.name.value == null || user.name.value!!.isEmpty()) errors["name"] =
            "Please enter valid name."
        if (user.email.value == null || !user.email.value!!.contains("@")) errors["email"] =
            "Please enter valid email address."
        if (!user.roles.value!!.any()) errors["roles"] = "Please select at least one role."
        return LoadingState(errors.isEmpty(), errors)
    }

    fun setArguments(bundle: Bundle) {
        this.id = bundle.getString("id")
        this.name.postValue(bundle.getString("name"))
        this.email.postValue(bundle.getString("email"))
        val stringArrayList = bundle.getStringArray("roles")
        if (stringArrayList != null) {
            this.roles.value = stringArrayList.toList()

        }

    }
}