package com.dpfht.tmdbmvp.feature.moviedetails

import com.dpfht.tmdbmvp.data.api.CallbackWrapper
import com.dpfht.tmdbmvp.data.model.remote.response.MovieDetailsResponse
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsModel

class MovieDetailsModelImpl(
  private val appRepository: AppRepository
): MovieDetailsModel {

  override fun getMovieDetails(
    movieId: Int,
    onSuccess: (MovieDetailsResponse) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    appRepository.getMovieDetail(movieId).enqueue(object : CallbackWrapper<MovieDetailsResponse>() {
      override fun onSuccessCall(responseBody: MovieDetailsResponse) {
        onSuccess(responseBody)
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
