package com.dpfht.tmdbmvp.feature.moviedetails.di

import com.dpfht.tmdbmvp.di.ApplicationComponent
import com.dpfht.tmdbmvp.di.FragmentScope
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsFragment
import dagger.Component

@Component(dependencies = [ApplicationComponent::class], modules = [MovieDetailsModule::class])
@MovieDetailsScope
@FragmentScope
interface MovieDetailsComponent {

  fun inject(movieDetailsFragment: MovieDetailsFragment)
}
