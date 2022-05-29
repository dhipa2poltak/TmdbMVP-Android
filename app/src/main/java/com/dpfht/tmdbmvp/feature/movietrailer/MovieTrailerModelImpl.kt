package com.dpfht.tmdbmvp.feature.movietrailer

import com.dpfht.tmdbmvp.data.api.CallbackWrapper
import com.dpfht.tmdbmvp.data.model.Trailer
import com.dpfht.tmdbmvp.data.model.response.TrailerResponse
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerModel

class MovieTrailerModelImpl(
  private val appRepository: AppRepository
): MovieTrailerModel {

  override fun getMovieTrailer(
    movieId: Int,
    onSuccess: (List<Trailer>) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    appRepository.getMovieTrailer(movieId).enqueue(object : CallbackWrapper<TrailerResponse?>() {
      override fun onSuccessCall(t: TrailerResponse?) {
        t?.results?.let {
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
