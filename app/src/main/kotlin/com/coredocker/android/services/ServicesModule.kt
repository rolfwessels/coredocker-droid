package com.coredocker.android.services

import org.koin.dsl.module.module

val servicesModule = module {
    single { Navigate() }
    single { SnackBarNotification() }
}
