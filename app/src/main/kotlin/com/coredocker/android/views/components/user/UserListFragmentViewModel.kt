package com.coredocker.android.views.components.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coredocker.android.data.network.graphql.PagedFragment
import com.coredocker.android.data.repository.DataRepository
import com.coredocker.android.services.Navigate
import com.coredocker.android.services.SnackBarNotification
import com.coredocker.android.util.extensions.handleErrors
import com.coredocker.fragment.UserFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserListFragmentViewModel(
    private val dataRepository: DataRepository,
    private val navigate: Navigate,
    snackBarNotification: SnackBarNotification
) : ViewModel() {
    val allUsers: Flow<PagedFragment<UserFragment>> =
        dataRepository.allUsers().handleErrors(snackBarNotification)
    val isLoading = MutableLiveData(false)

    fun onAddTap() {
        navigate.toUserCreateUpdate(null)
    }

    fun onEditTap(user: UserFragment) {
        navigate.toUserCreateUpdate(user)
    }

    fun onDeleteTap(pos: Any) {
        if (pos is UserFragment) {
            viewModelScope.launch { dataRepository.userRemove(pos.id) }
        }
    }
}