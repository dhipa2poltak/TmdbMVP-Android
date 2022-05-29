package com.dpfht.tmdbmvp.feature.moviereviews

import com.dpfht.tmdbmvp.base.BasePresenter
import com.dpfht.tmdbmvp.base.BaseView
import com.dpfht.tmdbmvp.data.model.Review

interface MovieReviewsContract {

  interface MovieReviewsView: BaseView {
    fun notifyItemInserted(position: Int)
  }

  interface MovieReviewsPresenter: BasePresenter {
    fun isLoadingData(): Boolean
    fun isEmptyReviews(): Boolean
    fun setMovieIdValue(movieId: Int)
    fun getMovieReviews()
  }

  interface MovieReviewsModel {
    fun getMovieReviews(
      movieId: Int,
      page: Int,
      onSuccess: (List<Review>, Int) -> Unit,
      onError: (String) -> Unit,
      onCancel: () -> Unit
    )
  }
}
