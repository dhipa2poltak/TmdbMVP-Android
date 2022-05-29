package com.dpfht.tmdbmvp.feature.moviesbygenre

import androidx.navigation.NavDirections
import com.dpfht.tmdbmvp.base.BasePresenter
import com.dpfht.tmdbmvp.base.BaseView
import com.dpfht.tmdbmvp.data.model.Movie

interface MoviesByGenreContract {

  interface MoviesByGenreView: BaseView {
    fun notifyItemInserted(position: Int)
  }

  interface MoviesByGenrePresenter: BasePresenter {
    var isLoadingData: Boolean
    val movies: ArrayList<Movie>

    fun setGenreIdValue(genreId: Int)
    fun getMoviesByGenre()
    fun getNavDirectionsOnClickMovieAt(position: Int): NavDirections
  }

  interface MoviesByGenreModel {
    fun getMoviesByGenre(
      genreId: Int,
      page: Int,
      onSuccess: (List<Movie>, Int) -> Unit,
      onError: (String) -> Unit,
      onCancel: () -> Unit
    )

    fun onDestroy()
  }
}
