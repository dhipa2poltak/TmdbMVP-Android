package com.dpfht.tmdbmvp.feature.moviedetails

import androidx.navigation.NavDirections
import com.dpfht.tmdbmvp.base.BasePresenter
import com.dpfht.tmdbmvp.base.BaseView
import com.dpfht.tmdbmvp.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper

interface MovieDetailsContract {

  interface MovieDetailsView: BaseView {
    fun showMovieDetails(title: String, overview: String, imageUrl: String)
  }

  interface MovieDetailsPresenter: BasePresenter {
    fun setMovieId(movieId: Int)
    fun getMovieId(): Int
    fun getNavDirectionsToMovieReviews(): NavDirections
  }

  interface MovieDetailsModel {
    suspend fun getMovieDetails(movieId: Int): ModelResultWrapper<GetMovieDetailsResult>
  }
}
