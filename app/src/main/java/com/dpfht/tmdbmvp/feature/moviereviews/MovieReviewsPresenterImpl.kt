package com.dpfht.tmdbmvp.feature.moviereviews

import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsModel
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsPresenter
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsView
import com.dpfht.tmdbmvp.data.model.Review

class MovieReviewsPresenterImpl(
  private var movieReviewsView: MovieReviewsView? = null,
  private var movieReviewsModel: MovieReviewsModel? = null,
  private val reviews: ArrayList<Review>
): MovieReviewsPresenter {

  private var _isLoadingData = false
  private var _movieId = -1
  private var page = 0

  override fun start() {
    if (_movieId != -1 && reviews.isEmpty()) {
      getMovieReviews()
    }
  }

  override fun isLoadingData() = _isLoadingData

  override fun setMovieId(movieId: Int) {
    this._movieId = movieId
  }

  override fun getMovieReviews() {
    movieReviewsView?.showLoadingDialog()
    _isLoadingData = true
    movieReviewsModel?.getMovieReviews(
      _movieId, page + 1, this::onSuccess, this::onError, this::onCancel
    )
  }

  fun onSuccess(reviews: List<Review>, page: Int) {
    if (reviews.isNotEmpty()) {
      this.page = page

      for (review in reviews) {
        this.reviews.add(review)
        movieReviewsView?.notifyItemInserted(this.reviews.size - 1)
      }
    }

    movieReviewsView?.hideLoadingDialog()
    _isLoadingData = false
  }

  fun onError(message: String) {
    movieReviewsView?.hideLoadingDialog()
    _isLoadingData = false
    movieReviewsView?.showErrorMessage(message)
  }

  fun onCancel() {
    movieReviewsView?.hideLoadingDialog()
    _isLoadingData = false
    movieReviewsView?.showCanceledMessage()
  }

  override fun onDestroy() {
    this.movieReviewsView = null
    this.movieReviewsModel?.onDestroy()
    this.movieReviewsModel = null
  }
}
