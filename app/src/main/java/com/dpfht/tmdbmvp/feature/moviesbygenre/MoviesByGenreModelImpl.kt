package com.dpfht.tmdbmvp.feature.moviesbygenre

import com.dpfht.tmdbmvp.data.api.CallbackWrapper
import com.dpfht.tmdbmvp.data.model.remote.Movie
import com.dpfht.tmdbmvp.data.model.remote.response.DiscoverMovieByGenreResponse
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreModel

class MoviesByGenreModelImpl(
  private val appRepository: AppRepository
): MoviesByGenreModel {

  override fun getMoviesByGenre(
    genreId: Int,
    page: Int,
    onSuccess: (List<Movie>, Int) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    appRepository.getMoviesByGenre(genreId.toString(), page).enqueue(object : CallbackWrapper<DiscoverMovieByGenreResponse>() {
      override fun onSuccessCall(responseBody: DiscoverMovieByGenreResponse) {
        responseBody.results?.let {
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
  }
}
