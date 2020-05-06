package com.coredocker.android

import androidx.multidex.MultiDexApplication
import com.coredocker.android.data.database.databaseModule
import com.coredocker.android.data.inject.appModule
import com.coredocker.android.data.inject.sharedPreferencesModule
import com.coredocker.android.data.network.networkModule
import com.coredocker.android.data.repository.repositoryModule
import com.coredocker.android.services.servicesModule
import com.coredocker.android.views.screen.viewModelModule
import org.koin.android.ext.android.startKoin

class MainApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin(
            this,
            listOf(
                appModule,
                networkModule,
                repositoryModule,
                viewModelModule,
                databaseModule,
                sharedPreferencesModule,
                servicesModule
            )
        )
    }
}