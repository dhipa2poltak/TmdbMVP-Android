package com.dpfht.tmdbmvp.feature.movietrailer.di

import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.di.ActivityScope
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerActivity
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerModel
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerPresenter
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerView
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerModelImpl
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerPresenterImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

@Module
class MovieTrailerModule(private val movieTrailerActivity: MovieTrailerActivity) {

  @Provides
  @ActivityScope
  fun provideMovieTrailerView(): MovieTrailerView {
    return movieTrailerActivity
  }

  @Provides
  @ActivityScope
  fun provideJob(): Job {
    return Job()
  }

  @Provides
  @ActivityScope
  fun provideCoroutineScope(job: Job): CoroutineScope {
    return CoroutineScope(job)
  }

  @Provides
  @ActivityScope
  fun provideMovieTrailerModel(appRepository: AppRepository, scope: CoroutineScope): MovieTrailerModel {
    return MovieTrailerModelImpl(appRepository, scope)
  }

  @Provides
  @ActivityScope
  fun provideMovieTrailerPresenter(
    movieTrailerView: MovieTrailerView,
    movieTrailerModel: MovieTrailerModel
  ): MovieTrailerPresenter {
    return MovieTrailerPresenterImpl(movieTrailerView, movieTrailerModel)
  }
}
