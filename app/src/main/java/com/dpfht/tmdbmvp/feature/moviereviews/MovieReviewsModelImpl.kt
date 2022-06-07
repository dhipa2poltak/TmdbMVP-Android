package com.dpfht.tmdbmvp.feature.moviereviews

import com.dpfht.tmdbmvp.data.api.CallbackWrapper
import com.dpfht.tmdbmvp.data.model.remote.Review
import com.dpfht.tmdbmvp.data.model.remote.response.ReviewResponse
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieReviewsModelImpl(
  private val appRepository: AppRepository,
  private val compositeDisposable: CompositeDisposable
): MovieReviewsModel {

  override fun getMovieReviews(
    movieId: Int,
    page: Int,
    onSuccess: (List<Review>, Int) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    val subs = appRepository.getMovieReviews(movieId, page)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeWith(object : CallbackWrapper<ReviewResponse?>() {
        override fun onSuccessCall(responseBody: ReviewResponse?) {
          responseBody?.results?.let {
            onSuccess(it, responseBody.page)
          }
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

  override fun onDestroy() {
    compositeDisposable.dispose()
  }
}
