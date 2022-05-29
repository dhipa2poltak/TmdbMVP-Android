package com.dpfht.tmdbmvp.feature.moviereviews

import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsModel
import com.dpfht.tmdbmvp.data.model.Review
import com.dpfht.tmdbmvp.data.model.response.ReviewResponse
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.util.ErrorUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MovieReviewsModelImpl(
  val appRepository: AppRepository
): MovieReviewsModel {

  override fun getMovieReviews(
    movieId: Int,
    page: Int,
    onSuccess: (List<Review>, Int) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    appRepository.getMovieReviews(movieId, page).enqueue(object : Callback<ReviewResponse?> {
      override fun onResponse(call: Call<ReviewResponse?>, response: Response<ReviewResponse?>) {
        if (response.isSuccessful) {
          response.body()?.let { resp ->
            resp.results?.let {
              onSuccess(it, resp.page)
            }
          }
        } else {
          val errorResponse = ErrorUtil.parseApiError(response)

          onError(errorResponse.statusMessage ?: "")
        }
      }

      override fun onFailure(call: Call<ReviewResponse?>, t: Throwable) {
        if (call.isCanceled) {
          onCancel()
        } else {
          if (t is IOException) {
            onError("error in connection")
          } else {
            onError("error in conversion")
          }
        }
      }
    })
  }
}
