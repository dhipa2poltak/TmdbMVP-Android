package com.dpfht.tmdbmvp.feature.genre

import com.dpfht.tmdbmvp.data.api.ResultWrapper.GenericError
import com.dpfht.tmdbmvp.data.api.ResultWrapper.NetworkError
import com.dpfht.tmdbmvp.data.api.ResultWrapper.Success
import com.dpfht.tmdbmvp.data.model.Genre
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenreModelImpl(
  private val appRepository: AppRepository,
  private val scope: CoroutineScope
): GenreModel {

  override fun getMovieGenre(
    onSuccess: (List<Genre>) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    scope.launch(Dispatchers.Main) {
      when (val responseBody = appRepository.getMovieGenre()) {      // switch to IO
        is Success -> {
          val genres = responseBody.value.genres ?: arrayListOf()
          onSuccess(genres)
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
