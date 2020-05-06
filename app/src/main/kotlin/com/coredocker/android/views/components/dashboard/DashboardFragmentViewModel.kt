package com.coredocker.android.views.components.dashboard

import androidx.lifecycle.ViewModel
import com.coredocker.android.data.repository.AuthenticationRepository

class DashboardFragmentViewModel(
    private val dataRepository: AuthenticationRepository
) : ViewModel() {
    fun isLoggedIn(): Boolean {
        return dataRepository.isLoggedIn()
    }


}