package com.dpfht.tmdbmvp.feature.movietrailer.di

import com.dpfht.tmdbmvp.di.ActivityScope
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerActivity
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerModel
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerPresenter
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerView
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerModelImpl
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerPresenterImpl
import com.dpfht.tmdbmvp.data.repository.AppRepository
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class MovieTrailerModule(private val movieTrailerActivity: MovieTrailerActivity) {

  @Provides
  @ActivityScope
  fun provideMovieTrailerView(): MovieTrailerView {
    return movieTrailerActivity
  }

  @Provides
  @ActivityScope
  fun provideMovieTrailerModel(
    appRepository: AppRepository,
    compositeDisposable: CompositeDisposable
  ): MovieTrailerModel {
    return MovieTrailerModelImpl(appRepository, compositeDisposable)
  }

  @Provides
  @ActivityScope
  fun provideMovieTrailerPresenter(
    movieTrailerView: MovieTrailerView,
    movieTrailerModel: MovieTrailerModel
  ): MovieTrailerPresenter {
    return MovieTrailerPresenterImpl(movieTrailerView, movieTrailerModel)
  }

  @Provides
  @ActivityScope
  fun provideCompositeDisposable(): CompositeDisposable {
    return CompositeDisposable()
  }
}
