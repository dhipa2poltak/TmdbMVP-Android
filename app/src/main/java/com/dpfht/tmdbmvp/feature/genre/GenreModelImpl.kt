package com.dpfht.tmdbmvp.feature.genre

import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreModel
import com.dpfht.tmdbmvp.data.model.Genre
import com.dpfht.tmdbmvp.data.model.response.GenreResponse
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.util.ErrorUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class GenreModelImpl(
  val appRepository: AppRepository
): GenreModel {

  override fun getMovieGenre(
    onSuccess: (List<Genre>) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    appRepository.getMovieGenre().enqueue(object : Callback<GenreResponse?> {
      override fun onResponse(call: Call<GenreResponse?>, response: Response<GenreResponse?>) {
        if (response.isSuccessful) {
          response.body()?.genres?.let {
            onSuccess(it)
          }
        } else {
          val errorResponse = ErrorUtil.parseApiError(response)

          onError(errorResponse.statusMessage ?: "")
        }
      }

      override fun onFailure(call: Call<GenreResponse?>, t: Throwable) {
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