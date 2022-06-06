package com.dpfht.tmdbmvp.feature.movietrailer

import com.dpfht.tmdbmvp.base.BasePresenter
import com.dpfht.tmdbmvp.domain.model.GetMovieTrailerResult
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper

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
    suspend fun getMovieTrailer(movieId: Int): ModelResultWrapper<GetMovieTrailerResult>
  }
}
