package com.dpfht.tmdbmvp.di

import android.content.Context
import com.dpfht.tmdbmvp.TheApplication
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.data.repository.AppRepositoryImpl
import com.dpfht.tmdbmvp.data.api.RestService
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
