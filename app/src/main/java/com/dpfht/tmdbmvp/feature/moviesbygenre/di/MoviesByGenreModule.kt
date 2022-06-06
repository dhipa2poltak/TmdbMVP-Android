package com.dpfht.tmdbmvp.feature.moviesbygenre.di

import android.content.Context
import androidx.lifecycle.lifecycleScope
import com.dpfht.tmdbmvp.data.model.remote.Movie
import com.dpfht.tmdbmvp.di.ActivityContext
import com.dpfht.tmdbmvp.di.FragmentModule
import com.dpfht.tmdbmvp.di.FragmentScope
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreModel
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenrePresenter
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreView
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreFragment
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreModelImpl
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenrePresenterImpl
import com.dpfht.tmdbmvp.feature.moviesbygenre.adapter.MoviesByGenreAdapter
import com.dpfht.tmdbmvp.data.repository.AppRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope

@Module(includes = [FragmentModule::class])
class MoviesByGenreModule(private val moviesByGenreFragment: MoviesByGenreFragment) {

  @Provides
  @FragmentScope
  @ActivityContext
  fun getContext(): Context {
    return moviesByGenreFragment.requireActivity()
  }

  @Provides
  @FragmentScope
  fun provideMoviesByGenreView(): MoviesByGenreView {
    return moviesByGenreFragment
  }

  @Provides
  @FragmentScope
  fun provideCoroutineScope(): CoroutineScope {
    return moviesByGenreFragment.lifecycleScope
  }

  @Provides
  @FragmentScope
  fun provideMoviesByGenreModel(appRepository: AppRepository): MoviesByGenreModel {
    return MoviesByGenreModelImpl(appRepository)
  }

  @Provides
  @FragmentScope
  fun provideMovies(): ArrayList<Movie> {
    return arrayListOf()
  }

  @Provides
  @FragmentScope
  fun provideMoviesByGenrePresenter(
    moviesByGenreView: MoviesByGenreView,
    moviesByGenreModel: MoviesByGenreModel,
    movies: ArrayList<Movie>,
    scope: CoroutineScope
  ): MoviesByGenrePresenter {
    return MoviesByGenrePresenterImpl(moviesByGenreView, moviesByGenreModel, movies, scope)
  }

  @Provides
  @FragmentScope
  fun provideMoviesByGenreAdapter(movies: ArrayList<Movie>): MoviesByGenreAdapter {
    return MoviesByGenreAdapter(movies)
  }
}
