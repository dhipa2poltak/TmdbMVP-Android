package com.dpfht.tmdbmvp.feature.moviesbygenre

import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreModel
import com.dpfht.tmdbmvp.data.model.Movie
import com.dpfht.tmdbmvp.data.model.response.DiscoverMovieByGenreResponse
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.util.ErrorUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MoviesByGenreModelImpl(
  val appRepository: AppRepository
): MoviesByGenreModel {

  override fun getMoviesByGenre(
    genreId: Int,
    page: Int,
    onSuccess: (List<Movie>, Int) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    appRepository.getMoviesByGenre(genreId.toString(), page).enqueue(object : Callback<DiscoverMovieByGenreResponse?> {
      override fun onResponse(
        call: Call<DiscoverMovieByGenreResponse?>,
        response: Response<DiscoverMovieByGenreResponse?>
      ) {
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

      override fun onFailure(call: Call<DiscoverMovieByGenreResponse?>, t: Throwable) {
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
