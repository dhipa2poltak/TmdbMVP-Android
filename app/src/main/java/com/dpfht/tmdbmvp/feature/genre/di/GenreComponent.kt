package com.dpfht.tmdbmvp.feature.genre.di

import com.dpfht.tmdbmvp.di.ApplicationComponent
import com.dpfht.tmdbmvp.di.FragmentScope
import com.dpfht.tmdbmvp.feature.genre.GenreFragment
import dagger.Component

@Component(dependencies = [ApplicationComponent::class], modules = [GenreModule::class])
@GenreScope
@FragmentScope
interface GenreComponent {

  fun inject(genreFragment: GenreFragment)
}
