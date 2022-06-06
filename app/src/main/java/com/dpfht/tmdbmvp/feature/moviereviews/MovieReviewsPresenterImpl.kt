package com.dpfht.tmdbmvp.feature.moviereviews

import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsModel
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsPresenter
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsView
import com.dpfht.tmdbmvp.data.model.remote.Review
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper.ErrorResult
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper.Success
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MovieReviewsPresenterImpl(
  private var movieReviewsView: MovieReviewsView? = null,
  private var movieReviewsModel: MovieReviewsModel? = null,
  private val reviews: ArrayList<Review>,
  private val scope: CoroutineScope
): MovieReviewsPresenter {

  private var _isLoadingData = false
  private var _movieId = -1
  private var page = 0
  private var isNextEmptyDataResponse = false

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
    if (isNextEmptyDataResponse) return

    scope.launch(Dispatchers.Main) {
      movieReviewsView?.showLoadingDialog()
      _isLoadingData = true

      movieReviewsModel?.let {
        when (val result = it.getMovieReviews(_movieId, page + 1)) {
          is Success -> {
            onSuccess(result.value.reviews, result.value.page)
          }
          is ErrorResult -> {
            onError(result.message)
          }
        }
      }
    }
  }

  private fun onSuccess(reviews: List<Review>, page: Int) {
    if (reviews.isNotEmpty()) {
      this.page = page

      for (review in reviews) {
        this.reviews.add(review)
        movieReviewsView?.notifyItemInserted(this.reviews.size - 1)
      }
    } else {
      isNextEmptyDataResponse = true
    }

    movieReviewsView?.hideLoadingDialog()
    _isLoadingData = false
  }

  private fun onError(message: String) {
    movieReviewsView?.hideLoadingDialog()
    _isLoadingData = false
    movieReviewsView?.showErrorMessage(message)
  }

  override fun onDestroy() {
    if (scope.isActive) {
      scope.cancel()
    }
    this.movieReviewsView = null
  }
}
