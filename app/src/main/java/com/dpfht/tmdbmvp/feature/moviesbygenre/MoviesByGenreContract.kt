package com.dpfht.tmdbmvp.feature.moviesbygenre

import androidx.navigation.NavDirections
import com.dpfht.tmdbmvp.base.BasePresenter
import com.dpfht.tmdbmvp.base.BaseView
import com.dpfht.tmdbmvp.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper

interface MoviesByGenreContract {

  interface MoviesByGenreView: BaseView {
    fun notifyItemInserted(position: Int)
  }

  interface MoviesByGenrePresenter: BasePresenter {
    fun isLoadingData(): Boolean
    fun setGenreId(genreId: Int)
    fun getMoviesByGenre()
    fun getNavDirectionsOnClickMovieAt(position: Int): NavDirections
  }

  interface MoviesByGenreModel {
    suspend fun getMoviesByGenre(genreId: Int, page: Int): ModelResultWrapper<GetMovieByGenreResult>
  }
}
