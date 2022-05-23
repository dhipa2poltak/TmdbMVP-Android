package com.dpfht.tmdbmvp.feature.movietrailer.di

import com.dpfht.tmdbmvp.di.ActivityScope
import com.dpfht.tmdbmvp.di.ApplicationComponent
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerActivity
import dagger.Component

@Component(dependencies = [ApplicationComponent::class], modules = [MovieTrailerModule::class])
@ActivityScope
interface MovieTrailerComponent {

  fun inject(movieTrailerActivity: MovieTrailerActivity)
}
