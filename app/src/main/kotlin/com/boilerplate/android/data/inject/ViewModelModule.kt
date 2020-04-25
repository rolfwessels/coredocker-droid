package com.boilerplate.android.data.inject

import com.boilerplate.android.base.feature.dashboard.DashboardFragmentViewModel
import com.boilerplate.android.base.feature.launchscreen.LaunchScreenViewModel

import com.boilerplate.android.base.feature.loginscreen.LoginScreenViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { LaunchScreenViewModel(get()) }
    viewModel { DashboardFragmentViewModel(get()) }
    viewModel { LoginScreenViewModel(get()) }
}