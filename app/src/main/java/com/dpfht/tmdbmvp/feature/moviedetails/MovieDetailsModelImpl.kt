package com.dpfht.tmdbmvp.feature.moviedetails

import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsModel
import com.dpfht.tmdbmvp.model.response.MovieDetailsResponse
import com.dpfht.tmdbmvp.repository.AppRepository
import com.dpfht.tmdbmvp.util.ErrorUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MovieDetailsModelImpl(
  val appRepository: AppRepository
): MovieDetailsModel {

  override fun getMovieDetails(
    movieId: Int,
    onSuccess: (MovieDetailsResponse) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    appRepository.getMovieDetail(movieId).enqueue(object : Callback<MovieDetailsResponse?> {
      override fun onResponse(
        call: Call<MovieDetailsResponse?>,
        response: Response<MovieDetailsResponse?>
      ) {
        if (response.isSuccessful) {
          response.body()?.let {
            onSuccess(it)
          }
        } else {
          val errorResponse = ErrorUtil.parseApiError(response)

          onError(errorResponse.statusMessage ?: "")
        }
      }

      override fun onFailure(call: Call<MovieDetailsResponse?>, t: Throwable) {
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