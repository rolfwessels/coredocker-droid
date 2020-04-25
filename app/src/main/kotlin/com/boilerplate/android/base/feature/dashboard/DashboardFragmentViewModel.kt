package com.boilerplate.android.base.feature.dashboard

import androidx.lifecycle.ViewModel
import com.boilerplate.android.data.repository.DataRepository

class DashboardFragmentViewModel(
    private val dataRepository: DataRepository
) : ViewModel() {
    fun isLoggedIn(): Boolean {
        return dataRepository.isLoggedIn()
    }


}