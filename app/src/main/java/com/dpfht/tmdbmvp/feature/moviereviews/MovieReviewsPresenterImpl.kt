package com.dpfht.tmdbmvp.feature.moviereviews

import com.dpfht.tmdbmvp.data.api.CallbackWrapper
import com.dpfht.tmdbmvp.data.model.remote.Review
import com.dpfht.tmdbmvp.domain.model.GetMovieReviewResult
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsModel
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsPresenter
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieReviewsPresenterImpl(
  private var movieReviewsView: MovieReviewsView? = null,
  private var movieReviewsModel: MovieReviewsModel? = null,
  private val reviews: ArrayList<Review>,
  private val compositeDisposable: CompositeDisposable
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

    movieReviewsView?.showLoadingDialog()
    _isLoadingData = true

    movieReviewsModel?.let { model ->
      val subs = model.getMovieReviews(_movieId, page + 1)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(object : CallbackWrapper<GetMovieReviewResult>() {
          override fun onSuccessCall(result: GetMovieReviewResult) {
            onSuccess(result.reviews, result.page)
          }

          override fun onErrorCall(message: String) {
            onError(message)
          }

          override fun onCancelCall() {
            onCancel()
          }
        })

      compositeDisposable.add(subs)
    }
  }

  fun onSuccess(reviews: List<Review>, page: Int) {
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
    if (!compositeDisposable.isDisposed) {
      compositeDisposable.dispose()
    }
    this.movieReviewsView = null
    this.movieReviewsModel = null
  }
}
