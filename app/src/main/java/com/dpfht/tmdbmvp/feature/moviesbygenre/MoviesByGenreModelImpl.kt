package com.dpfht.tmdbmvp.feature.moviesbygenre

import com.dpfht.tmdbmvp.data.api.ResultWrapper.GenericError
import com.dpfht.tmdbmvp.data.api.ResultWrapper.NetworkError
import com.dpfht.tmdbmvp.data.api.ResultWrapper.Success
import com.dpfht.tmdbmvp.data.model.Movie
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesByGenreModelImpl(
  private val appRepository: AppRepository,
  private val scope: CoroutineScope
): MoviesByGenreModel {

  override fun getMoviesByGenre(
    genreId: Int,
    page: Int,
    onSuccess: (List<Movie>, Int) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    scope.launch(Dispatchers.Main) {
      when (val responseBody = appRepository.getMoviesByGenre(genreId.toString(), page)) {  // switch to Dispatchers.IO in Repository
        is Success -> {
          val movies = responseBody.value.results ?: arrayListOf()
          onSuccess(movies, responseBody.value.page)
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
