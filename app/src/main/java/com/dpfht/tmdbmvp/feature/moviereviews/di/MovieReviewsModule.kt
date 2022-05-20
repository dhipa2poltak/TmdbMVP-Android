package com.dpfht.tmdbmvp.feature.moviereviews.di

import android.content.Context
import com.dpfht.tmdbmvp.di.ActivityContext
import com.dpfht.tmdbmvp.di.FragmentModule
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsModel
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsPresenter
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsView
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsFragment
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsModelImpl
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsPresenterImpl
import com.dpfht.tmdbmvp.feature.moviereviews.adapter.MovieReviewsAdapter
import com.dpfht.tmdbmvp.repository.AppRepository
import dagger.Module
import dagger.Provides

@Module(includes = [FragmentModule::class])
class MovieReviewsModule(private val movieReviewsFragment: MovieReviewsFragment) {

  @Provides
  @MovieReviewsScope
  @ActivityContext
  fun getContext(): Context {
    return movieReviewsFragment.requireActivity()
  }

  @Provides
  @MovieReviewsScope
  fun provideMovieReviewsView(): MovieReviewsView {
    return movieReviewsFragment
  }

  @Provides
  @MovieReviewsScope
  fun provideMovieReviewsModel(appRepository: AppRepository): MovieReviewsModel {
    return MovieReviewsModelImpl(appRepository)
  }

  @Provides
  @MovieReviewsScope
  fun provideMovieReviewsPresenter(
    movieReviewsView: MovieReviewsView,
    movieReviewsModel: MovieReviewsModel
  ): MovieReviewsPresenter {
    return MovieReviewsPresenterImpl(movieReviewsView, movieReviewsModel)
  }

  @Provides
  @MovieReviewsScope
  fun provideMovieReviewsAdapter(movieReviewsPresenter: MovieReviewsPresenter): MovieReviewsAdapter {
    return MovieReviewsAdapter(movieReviewsPresenter.reviews)
  }
}
