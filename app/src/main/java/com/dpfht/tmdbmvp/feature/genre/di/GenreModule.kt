package com.dpfht.tmdbmvp.feature.genre.di

import android.content.Context
import com.dpfht.tmdbmvp.di.ActivityContext
import com.dpfht.tmdbmvp.di.FragmentModule
import com.dpfht.tmdbmvp.di.FragmentScope
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreModel
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenrePresenter
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreView
import com.dpfht.tmdbmvp.feature.genre.GenreFragment
import com.dpfht.tmdbmvp.feature.genre.GenreModelImpl
import com.dpfht.tmdbmvp.feature.genre.GenrePresenterImpl
import com.dpfht.tmdbmvp.feature.genre.adapter.GenreAdapter
import com.dpfht.tmdbmvp.data.repository.AppRepository
import dagger.Module
import dagger.Provides

@Module(includes = [FragmentModule::class])
class GenreModule(private val genreFragment: GenreFragment) {

  @Provides
  @ActivityContext
  @FragmentScope
  fun getContext(): Context {
    return genreFragment.requireActivity()
  }

  @Provides
  @FragmentScope
  fun provideGenreView(): GenreView {
    return genreFragment
  }

  @Provides
  @FragmentScope
  fun provideGenreModel(appRepository: AppRepository): GenreModel {
    return GenreModelImpl(appRepository)
  }

  @Provides
  @FragmentScope
  fun provideGenrePresenter(genreView: GenreView, genreModel: GenreModel): GenrePresenter {
    return GenrePresenterImpl(genreView, genreModel)
  }

  @Provides
  @FragmentScope
  fun provideGenreAdapter(genrePresenter: GenrePresenter): GenreAdapter {
    return GenreAdapter(genrePresenter.genres)
  }
}
