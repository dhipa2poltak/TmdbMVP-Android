package com.dpfht.tmdbmvp

import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy.Builder
import android.os.StrictMode.VmPolicy
import androidx.multidex.MultiDex
import com.dpfht.tmdbmvp.di.ApplicationComponent
import com.dpfht.tmdbmvp.di.ApplicationModule
import com.dpfht.tmdbmvp.di.DaggerApplicationComponent
import com.dpfht.tmdbmvp.di.NetworkModule


class TheApplication: Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                Builder().detectAll()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                VmPolicy.Builder().detectAll()
                    .penaltyLog()
                    .build()
            )
        }

        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .networkModule(NetworkModule())
            .build()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        lateinit var instance: TheApplication
    }
}
