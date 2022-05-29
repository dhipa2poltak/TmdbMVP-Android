package com.dpfht.tmdbmvp.feature.movietrailer

import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerModel
import com.dpfht.tmdbmvp.data.model.Trailer
import com.dpfht.tmdbmvp.data.model.response.TrailerResponse
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.util.ErrorUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MovieTrailerModelImpl(
  val appRepository: AppRepository
): MovieTrailerModel {

  override fun getMovieTrailer(
    movieId: Int,
    onSuccess: (List<Trailer>) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    appRepository.getMovieTrailer(movieId).enqueue(object : Callback<TrailerResponse?> {
      override fun onResponse(call: Call<TrailerResponse?>, response: Response<TrailerResponse?>) {
        if (response.isSuccessful) {
          response.body()?.results?.let {
            onSuccess(it)
          }
        } else {
          val errorResponse = ErrorUtil.parseApiError(response)

          onError(errorResponse.statusMessage ?: "")
        }
      }

      override fun onFailure(call: Call<TrailerResponse?>, t: Throwable) {
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
