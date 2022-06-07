package com.dpfht.tmdbmvp.feature.genre

import com.dpfht.tmdbmvp.data.api.CallbackWrapper
import com.dpfht.tmdbmvp.data.model.remote.Genre
import com.dpfht.tmdbmvp.data.model.remote.response.GenreResponse
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreModel

class GenreModelImpl(
  private val appRepository: AppRepository
): GenreModel {

  override fun getMovieGenre(
    onSuccess: (List<Genre>) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    appRepository.getMovieGenre().enqueue(object : CallbackWrapper<GenreResponse?>() {
      override fun onSuccessCall(responseBody: GenreResponse?) {
        responseBody?.genres?.let { genres ->
          onSuccess(genres)
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