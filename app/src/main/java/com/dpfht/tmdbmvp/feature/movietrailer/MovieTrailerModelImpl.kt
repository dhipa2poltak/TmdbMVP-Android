package com.dpfht.tmdbmvp.feature.movietrailer

import com.dpfht.tmdbmvp.data.api.ResultWrapper.GenericError
import com.dpfht.tmdbmvp.data.api.ResultWrapper.NetworkError
import com.dpfht.tmdbmvp.data.api.ResultWrapper.Success
import com.dpfht.tmdbmvp.data.model.Trailer
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MovieTrailerModelImpl(
  private val appRepository: AppRepository,
  private val scope: CoroutineScope
): MovieTrailerModel {

  override fun getMovieTrailer(
    movieId: Int,
    onSuccess: (List<Trailer>) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    scope.launch(Dispatchers.Main) {
      when (val responseBody = appRepository.getMovieTrailer(movieId)) {    // switch to IO
        is Success -> {
          val trailers = responseBody.value.results ?: arrayListOf()
          onSuccess(trailers)
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

  override fun onDestroy() {
    scope.cancel()
  }
}
