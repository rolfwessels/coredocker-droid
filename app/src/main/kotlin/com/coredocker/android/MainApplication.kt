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
import timber.log.Timber
import timber.log.Timber.DebugTree

@kotlinx.coroutines.ExperimentalCoroutinesApi
class MainApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
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
            ), logger = org.koin.log.EmptyLogger()

        )
    }
}