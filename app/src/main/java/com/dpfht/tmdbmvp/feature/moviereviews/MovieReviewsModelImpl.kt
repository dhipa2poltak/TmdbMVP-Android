package com.dpfht.tmdbmvp.feature.moviereviews

import com.dpfht.tmdbmvp.data.api.ResultWrapper.GenericError
import com.dpfht.tmdbmvp.data.api.ResultWrapper.NetworkError
import com.dpfht.tmdbmvp.data.api.ResultWrapper.Success
import com.dpfht.tmdbmvp.data.model.Review
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieReviewsModelImpl(
  private val appRepository: AppRepository,
  private val scope: CoroutineScope
): MovieReviewsModel {

  override fun getMovieReviews(
    movieId: Int,
    page: Int,
    onSuccess: (List<Review>, Int) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    scope.launch(Dispatchers.Main) {
      when (val responseBody = appRepository.getMovieReviews(movieId, page)) {    // switch to IO
        is Success -> {
          val reviews = responseBody.value.results ?: arrayListOf()
          onSuccess(reviews, responseBody.value.page)
        }
        is GenericError -> {
          if (responseBody.code != null && responseBody.error != null) {
            onError(responseBody.error.statusMessage ?: "")
          } else {
            onError("error in conversion")
          }
        }
        is NetworkError -> {
          onError("error in connection")
        }
      }
    }
  }
}
