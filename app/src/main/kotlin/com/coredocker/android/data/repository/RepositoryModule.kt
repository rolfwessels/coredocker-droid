package com.coredocker.android.data.repository

import org.koin.dsl.module.module

val repositoryModule = module {
    single { AuthenticationRepository(get(),get()) }
    single { DataRepository(get()) }
}
