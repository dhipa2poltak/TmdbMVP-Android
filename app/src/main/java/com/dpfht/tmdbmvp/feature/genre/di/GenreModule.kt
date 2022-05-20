package com.dpfht.tmdbmvp.feature.genre.di

import android.content.Context
import com.dpfht.tmdbmvp.di.ActivityContext
import com.dpfht.tmdbmvp.di.FragmentModule
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreModel
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenrePresenter
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreView
import com.dpfht.tmdbmvp.feature.genre.GenreFragment
import com.dpfht.tmdbmvp.feature.genre.GenreModelImpl
import com.dpfht.tmdbmvp.feature.genre.GenrePresenterImpl
import com.dpfht.tmdbmvp.feature.genre.adapter.GenreAdapter
import com.dpfht.tmdbmvp.repository.AppRepository
import dagger.Module
import dagger.Provides

@Module(includes = [FragmentModule::class])
class GenreModule(private val genreFragment: GenreFragment) {

  @Provides
  @GenreScope
  @ActivityContext
  fun getContext(): Context {
    return genreFragment.requireActivity()
  }

  @Provides
  @GenreScope
  fun provideGenreView(): GenreView {
    return genreFragment
  }

  @Provides
  @GenreScope
  fun provideGenreModel(appRepository: AppRepository): GenreModel {
    return GenreModelImpl(appRepository)
  }

  @Provides
  @GenreScope
  fun provideGenrePresenter(genreView: GenreView, genreModel: GenreModel): GenrePresenter {
    return GenrePresenterImpl(genreView, genreModel)
  }

  @Provides
  @GenreScope
  fun provideGenreAdapter(genrePresenter: GenrePresenter): GenreAdapter {
    return GenreAdapter(genrePresenter.genres)
  }
}
