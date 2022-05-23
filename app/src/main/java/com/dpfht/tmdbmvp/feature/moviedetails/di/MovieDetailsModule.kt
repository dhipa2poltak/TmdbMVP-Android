package com.dpfht.tmdbmvp.feature.moviedetails.di

import android.content.Context
import com.dpfht.tmdbmvp.di.ActivityContext
import com.dpfht.tmdbmvp.di.FragmentModule
import com.dpfht.tmdbmvp.di.FragmentScope
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsModel
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsPresenter
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsView
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsFragment
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsModelImpl
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsPresenterImpl
import com.dpfht.tmdbmvp.repository.AppRepository
import dagger.Module
import dagger.Provides

@Module(includes = [FragmentModule::class])
class MovieDetailsModule(private val movieDetailsFragment: MovieDetailsFragment) {

  @Provides
  @FragmentScope
  @ActivityContext
  fun getContext(): Context {
    return movieDetailsFragment.requireActivity()
  }

  @Provides
  @FragmentScope
  fun provideMovieDetailsView(): MovieDetailsView {
    return movieDetailsFragment
  }

  @Provides
  @FragmentScope
  fun provideMovieDetailsModel(appRepository: AppRepository): MovieDetailsModel {
    return MovieDetailsModelImpl(appRepository)
  }

  @Provides
  @FragmentScope
  fun provideMovieDetailsPresenter(
    movieDetailsView: MovieDetailsView,
    movieDetailsModel: MovieDetailsModel
  ): MovieDetailsPresenter {
    return MovieDetailsPresenterImpl(movieDetailsView, movieDetailsModel)
  }
}
