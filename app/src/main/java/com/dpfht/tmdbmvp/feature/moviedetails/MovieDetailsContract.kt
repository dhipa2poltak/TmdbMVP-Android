package com.dpfht.tmdbmvp.feature.moviedetails

import com.dpfht.tmdbmvp.base.BasePresenter
import com.dpfht.tmdbmvp.base.BaseView
import com.dpfht.tmdbmvp.data.model.response.MovieDetailsResponse

interface MovieDetailsContract {

  interface MovieDetailsView: BaseView {
    fun showMovieDetails(title: String, overview: String, imageUrl: String)
  }

  interface MovieDetailsPresenter: BasePresenter {
    var movieId: Int
    var title: String
    var overview: String
    var imageUrl: String

    fun getMovieDetails(movieId: Int)
  }

  interface MovieDetailsModel {
    fun getMovieDetails(
      movieId: Int,
      onSuccess: (MovieDetailsResponse) -> Unit,
      onError: (String) -> Unit,
      onCancel: () -> Unit
    )
  }
}
