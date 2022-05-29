package com.dpfht.tmdbmvp.feature.moviereviews.di

import android.content.Context
import com.dpfht.tmdbmvp.di.ActivityContext
import com.dpfht.tmdbmvp.di.FragmentModule
import com.dpfht.tmdbmvp.di.FragmentScope
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsModel
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsPresenter
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsView
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsFragment
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsModelImpl
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsPresenterImpl
import com.dpfht.tmdbmvp.feature.moviereviews.adapter.MovieReviewsAdapter
import com.dpfht.tmdbmvp.data.repository.AppRepository
import dagger.Module
import dagger.Provides

@Module(includes = [FragmentModule::class])
class MovieReviewsModule(private val movieReviewsFragment: MovieReviewsFragment) {

  @Provides
  @FragmentScope
  @ActivityContext
  fun getContext(): Context {
    return movieReviewsFragment.requireActivity()
  }

  @Provides
  @FragmentScope
  fun provideMovieReviewsView(): MovieReviewsView {
    return movieReviewsFragment
  }

  @Provides
  @FragmentScope
  fun provideMovieReviewsModel(appRepository: AppRepository): MovieReviewsModel {
    return MovieReviewsModelImpl(appRepository)
  }

  @Provides
  @FragmentScope
  fun provideMovieReviewsPresenter(
    movieReviewsView: MovieReviewsView,
    movieReviewsModel: MovieReviewsModel
  ): MovieReviewsPresenter {
    return MovieReviewsPresenterImpl(movieReviewsView, movieReviewsModel)
  }

  @Provides
  @FragmentScope
  fun provideMovieReviewsAdapter(movieReviewsPresenter: MovieReviewsPresenter): MovieReviewsAdapter {
    return MovieReviewsAdapter(movieReviewsPresenter.reviews)
  }
}
