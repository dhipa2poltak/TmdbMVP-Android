package com.dpfht.tmdbmvp.feature.moviesbygenre.di

import android.content.Context
import com.dpfht.tmdbmvp.di.ActivityContext
import com.dpfht.tmdbmvp.di.FragmentModule
import com.dpfht.tmdbmvp.di.FragmentScope
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreModel
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenrePresenter
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreView
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreFragment
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreModelImpl
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenrePresenterImpl
import com.dpfht.tmdbmvp.feature.moviesbygenre.adapter.MoviesByGenreAdapter
import com.dpfht.tmdbmvp.repository.AppRepository
import dagger.Module
import dagger.Provides

@Module(includes = [FragmentModule::class])
class MoviesByGenreModule(private val moviesByGenreFragment: MoviesByGenreFragment) {

  @Provides
  @FragmentScope
  @ActivityContext
  fun getContext(): Context {
    return moviesByGenreFragment.requireActivity()
  }

  @Provides
  @FragmentScope
  fun provideMoviesByGenreView(): MoviesByGenreView {
    return moviesByGenreFragment
  }

  @Provides
  @FragmentScope
  fun provideMoviesByGenreModel(appRepository: AppRepository): MoviesByGenreModel {
    return MoviesByGenreModelImpl(appRepository)
  }

  @Provides
  @FragmentScope
  fun provideMoviesByGenrePresenter(
    moviesByGenreView: MoviesByGenreView,
    moviesByGenreModel: MoviesByGenreModel
  ): MoviesByGenrePresenter {
    return MoviesByGenrePresenterImpl(moviesByGenreView, moviesByGenreModel)
  }

  @Provides
  @FragmentScope
  fun provideMoviesByGenreAdapter(moviesByGenrePresenter: MoviesByGenrePresenter): MoviesByGenreAdapter {
    return MoviesByGenreAdapter(moviesByGenrePresenter.movies)
  }
}
