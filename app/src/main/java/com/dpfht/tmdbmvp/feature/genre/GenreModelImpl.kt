package com.dpfht.tmdbmvp.feature.genre

import com.dpfht.tmdbmvp.data.api.CallbackWrapper
import com.dpfht.tmdbmvp.data.model.Genre
import com.dpfht.tmdbmvp.data.model.response.GenreResponse
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreModel

class GenreModelImpl(
  val appRepository: AppRepository
): GenreModel {

  override fun getMovieGenre(
    onSuccess: (List<Genre>) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    appRepository.getMovieGenre().enqueue(object : CallbackWrapper<GenreResponse?>() {
      override fun onSuccessCall(t: GenreResponse?) {
        t?.genres?.let { genres ->
          onSuccess(genres)
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