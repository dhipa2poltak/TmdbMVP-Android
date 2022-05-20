package com.dpfht.tmdbmvp.feature.moviereviews.di

import com.dpfht.tmdbmvp.di.ApplicationComponent
import com.dpfht.tmdbmvp.di.FragmentScope
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsFragment
import dagger.Component

@Component(dependencies = [ApplicationComponent::class], modules = [MovieReviewsModule::class])
@MovieReviewsScope
@FragmentScope
interface MovieReviewsComponent {

  fun inject(movieReviewsFragment: MovieReviewsFragment)
}
