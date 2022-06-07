package com.dpfht.tmdbmvp.feature.movietrailer

import com.dpfht.tmdbmvp.base.BasePresenter
import com.dpfht.tmdbmvp.data.model.remote.Trailer

interface MovieTrailerContract {

  interface MovieTrailerView {
    fun showTrailer(keyVideo: String)
    fun showErrorMessage(message: String)
    fun showCanceledMessage()
  }

  interface MovieTrailerPresenter: BasePresenter {
    fun setMovieId(movieId: Int)
  }

  interface MovieTrailerModel {
    fun getMovieTrailer(
      movieId: Int,
      onSuccess: (List<Trailer>) -> Unit,
      onError: (String) -> Unit,
      onCancel: () -> Unit
    )
  }
}
