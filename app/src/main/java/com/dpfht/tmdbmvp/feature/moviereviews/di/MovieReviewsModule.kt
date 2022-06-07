package com.dpfht.tmdbmvp.feature.moviereviews.di

import android.content.Context
import com.dpfht.tmdbmvp.data.model.remote.Review
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
import io.reactivex.disposables.CompositeDisposable

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
  fun provideMovieReviewsModel(
    appRepository: AppRepository,
    compositeDisposable: CompositeDisposable
  ): MovieReviewsModel {
    return MovieReviewsModelImpl(appRepository, compositeDisposable)
  }

  @Provides
  @FragmentScope
  fun provideReviews(): ArrayList<Review> {
    return arrayListOf()
  }

  @Provides
  @FragmentScope
  fun provideMovieReviewsPresenter(
    movieReviewsView: MovieReviewsView,
    movieReviewsModel: MovieReviewsModel,
    reviews: ArrayList<Review>
  ): MovieReviewsPresenter {
    return MovieReviewsPresenterImpl(movieReviewsView, movieReviewsModel, reviews)
  }

  @Provides
  @FragmentScope
  fun provideMovieReviewsAdapter(reviews: ArrayList<Review>): MovieReviewsAdapter {
    return MovieReviewsAdapter(reviews)
  }

  @Provides
  @FragmentScope
  fun provideCompositeDisposable(): CompositeDisposable {
    return CompositeDisposable()
  }
}
