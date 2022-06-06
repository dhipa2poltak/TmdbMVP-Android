package com.dpfht.tmdbmvp.feature.moviereviews

import com.dpfht.tmdbmvp.base.BasePresenter
import com.dpfht.tmdbmvp.base.BaseView
import com.dpfht.tmdbmvp.domain.model.GetMovieReviewResult
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper

interface MovieReviewsContract {

  interface MovieReviewsView: BaseView {
    fun notifyItemInserted(position: Int)
  }

  interface MovieReviewsPresenter: BasePresenter {
    fun isLoadingData(): Boolean
    fun setMovieId(movieId: Int)
    fun getMovieReviews()
  }

  interface MovieReviewsModel {
    suspend fun getMovieReviews(movieId: Int, page: Int): ModelResultWrapper<GetMovieReviewResult>
  }
}
