package com.coredocker.android.data.inject

import org.koin.dsl.module.module
import java.util.Calendar

val appModule = module {
    single { provideCalendar() }
}

private fun provideCalendar() = Calendar.getInstance()
