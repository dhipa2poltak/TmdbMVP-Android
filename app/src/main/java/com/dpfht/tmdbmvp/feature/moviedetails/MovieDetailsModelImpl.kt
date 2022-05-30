package com.dpfht.tmdbmvp.feature.moviedetails

import com.dpfht.tmdbmvp.data.api.ResultWrapper.GenericError
import com.dpfht.tmdbmvp.data.api.ResultWrapper.NetworkError
import com.dpfht.tmdbmvp.data.api.ResultWrapper.Success
import com.dpfht.tmdbmvp.data.model.response.MovieDetailsResponse
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsModelImpl(
  private val appRepository: AppRepository,
  private val scope: CoroutineScope
): MovieDetailsModel {

  override fun getMovieDetails(
    movieId: Int,
    onSuccess: (MovieDetailsResponse) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    scope.launch(Dispatchers.Main) {
      when (val responseBody = appRepository.getMovieDetail(movieId)) {   // switch to IO
        is Success -> {
          onSuccess(responseBody.value)
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
