package com.dpfht.tmdbmvp.di

import com.dpfht.tmdbmvp.data.repository.AppRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {

    //fun getRestApiService(): RestService
    fun getAppRepository(): AppRepository
}