package com.dpfht.tmdbmvp.feature.moviereviews

import com.dpfht.tmdbmvp.base.BasePresenter
import com.dpfht.tmdbmvp.base.BaseView
import com.dpfht.tmdbmvp.domain.model.GetMovieReviewResult
import io.reactivex.Observable

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
    fun getMovieReviews(movieId: Int, page: Int): Observable<GetMovieReviewResult>
  }
}
