package com.coredocker.android.views.components.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coredocker.android.business.model.User
import com.coredocker.android.data.repository.DataRepository
import com.coredocker.android.services.Navigate
import com.coredocker.android.services.SnackBarNotification
import com.coredocker.android.util.extensions.handleErrors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserListFragmentViewModel(
    private val dataRepository: DataRepository,
    private val navigate: Navigate,
    snackBarNotification: SnackBarNotification
) : ViewModel() {
    val allUsers: Flow<List<User>> =
        dataRepository.allUsers().handleErrors(snackBarNotification)
    val isLoading = MutableLiveData(false)

    fun onAddTap() {
        navigate.toUserCreateUpdate(null)
    }

    fun onEditTap(user: User) {
        navigate.toUserCreateUpdate(user)
    }

    fun onDeleteTap(pos: Any) {
        if (pos is User) {
            viewModelScope.launch { dataRepository.removeUser(pos.id) }
        }
    }
}