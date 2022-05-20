package com.dpfht.tmdbmvp.di

import android.content.Context
import com.dpfht.tmdbmvp.TheApplication
import com.dpfht.tmdbmvp.repository.AppRepository
import com.dpfht.tmdbmvp.repository.AppRepositoryImpl
import com.dpfht.tmdbmvp.rest.RestService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val theApplication: TheApplication) {

    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return theApplication
    }

    @Singleton
    @Provides
    fun provideAppRepository(restService: RestService): AppRepository {
        return AppRepositoryImpl(restService)
    }
}
