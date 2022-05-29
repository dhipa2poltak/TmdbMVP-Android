package com.dpfht.tmdbmvp.feature.moviereviews

import com.dpfht.tmdbmvp.data.api.CallbackWrapper
import com.dpfht.tmdbmvp.data.model.Review
import com.dpfht.tmdbmvp.data.model.response.ReviewResponse
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsModel

class MovieReviewsModelImpl(
  private val appRepository: AppRepository
): MovieReviewsModel {

  override fun getMovieReviews(
    movieId: Int,
    page: Int,
    onSuccess: (List<Review>, Int) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    appRepository.getMovieReviews(movieId, page).enqueue(object : CallbackWrapper<ReviewResponse?>() {
      override fun onSuccessCall(t: ReviewResponse?) {
        t?.results?.let {
          onSuccess(it, t.page)
        }
      }

      override fun onErrorCall(str: String) {
        onError(str)
      }

      override fun onCancelCall() {
        onCancel()
      }
    })
  }
}
