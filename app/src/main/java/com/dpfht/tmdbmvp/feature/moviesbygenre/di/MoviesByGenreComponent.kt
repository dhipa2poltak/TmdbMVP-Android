package com.dpfht.tmdbmvp.feature.moviesbygenre.di

import com.dpfht.tmdbmvp.di.ApplicationComponent
import com.dpfht.tmdbmvp.di.FragmentScope
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreFragment
import dagger.Component

@Component(dependencies = [ApplicationComponent::class], modules = [MoviesByGenreModule::class])
@MoviesByGenreScope
@FragmentScope
interface MoviesByGenreComponent {

  fun inject(moviesByGenreFragment: MoviesByGenreFragment)
}
