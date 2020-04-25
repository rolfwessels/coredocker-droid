package com.boilerplate.android.base.feature.launchscreen

import androidx.lifecycle.ViewModel
import com.boilerplate.android.data.repository.DataRepository

class LaunchScreenViewModel(
    private val dataRepository: DataRepository
) : ViewModel() {
    fun isLoggedIn(): Boolean {
        return dataRepository.isLoggedIn()
    }


}