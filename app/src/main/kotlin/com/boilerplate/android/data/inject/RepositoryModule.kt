package com.boilerplate.android.data.inject

import com.boilerplate.android.data.repository.DataRepository
import org.koin.dsl.module.module

val repositoryModule = module {
    single { DataRepository(get(), get()) }
}