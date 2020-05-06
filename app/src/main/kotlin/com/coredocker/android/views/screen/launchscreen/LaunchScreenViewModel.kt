package com.coredocker.android.views.screen.launchscreen

import androidx.lifecycle.ViewModel
import com.coredocker.android.data.repository.AuthenticationRepository

class LaunchScreenViewModel(
    private val dataRepository: AuthenticationRepository
) : ViewModel() {
    fun isLoggedIn(): Boolean {
        return dataRepository.isLoggedIn()
    }
}