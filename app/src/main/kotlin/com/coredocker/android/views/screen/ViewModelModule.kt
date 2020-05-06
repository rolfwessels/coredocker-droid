package com.coredocker.android.views.screen

import com.coredocker.android.views.components.dashboard.DashboardFragmentViewModel
import com.coredocker.android.views.components.user.UserCreateUpdateFragmentViewModel
import com.coredocker.android.views.components.user.UserListFragmentViewModel
import com.coredocker.android.views.screen.launchscreen.LaunchScreenViewModel
import com.coredocker.android.views.screen.loginscreen.LoginScreenViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { LaunchScreenViewModel(get()) }
    viewModel { DashboardFragmentViewModel(get()) }
    viewModel { LoginScreenViewModel(get(),get(),get()) }
    viewModel { UserListFragmentViewModel(get(),get(),get()) }
    viewModel { UserCreateUpdateFragmentViewModel(get(), get(), get()) }
}
