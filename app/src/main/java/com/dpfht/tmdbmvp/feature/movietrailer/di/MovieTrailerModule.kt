package com.dpfht.tmdbmvp.feature.movietrailer.di

import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerActivity
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerModel
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerPresenter
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerView
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerModelImpl
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerPresenterImpl
import com.dpfht.tmdbmvp.repository.AppRepository
import dagger.Module
import dagger.Provides

@Module
class MovieTrailerModule(private val movieTrailerActivity: MovieTrailerActivity) {

  @Provides
  @MovieTrailerScope
  fun provideMovieTrailerView(): MovieTrailerView {
    return movieTrailerActivity
  }

  @Provides
  @MovieTrailerScope
  fun provideMovieTrailerModel(appRepository: AppRepository): MovieTrailerModel {
    return MovieTrailerModelImpl(appRepository)
  }

  @Provides
  @MovieTrailerScope
  fun provideMovieTrailerPresenter(
    movieTrailerView: MovieTrailerView,
    movieTrailerModel: MovieTrailerModel
  ): MovieTrailerPresenter {
    return MovieTrailerPresenterImpl(movieTrailerView, movieTrailerModel)
  }
}
