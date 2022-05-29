package com.dpfht.tmdbmvp.feature.moviedetails

import com.dpfht.tmdbmvp.data.api.CallbackWrapper
import com.dpfht.tmdbmvp.data.model.response.MovieDetailsResponse
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsModel

class MovieDetailsModelImpl(
  val appRepository: AppRepository
): MovieDetailsModel {

  override fun getMovieDetails(
    movieId: Int,
    onSuccess: (MovieDetailsResponse) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    appRepository.getMovieDetail(movieId).enqueue(object : CallbackWrapper<MovieDetailsResponse?>() {
      override fun onSuccessCall(t: MovieDetailsResponse?) {
        t?.let {
          onSuccess(it)
        }
      }

      override fun onErrorCall(str: String) {
        onError(str)
      }

      override fun onCancelCall() {
        onCancel()
      }
    })
  }
}
