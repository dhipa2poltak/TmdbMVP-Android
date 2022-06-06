package com.dpfht.tmdbmvp.feature.movietrailer

import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerModel
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerPresenter
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerView
import com.dpfht.tmdbmvp.data.model.remote.Trailer
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper.ErrorResult
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper.Success
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Locale

class MovieTrailerPresenterImpl(
  private var movieTrailerView: MovieTrailerView? = null,
  private var movieTrailerModel: MovieTrailerModel? = null,
  private val scope: CoroutineScope
): MovieTrailerPresenter {

  private var _movieId = -1

  override fun setMovieId(movieId: Int) {
    this._movieId = movieId
  }

  override fun start() {
    if (_movieId != -1) {
      getMovieTrailer()
    }
  }

  private fun getMovieTrailer() {
    scope.launch(Dispatchers.Main) {
      movieTrailerModel?.let {
        when (val result = it.getMovieTrailer(_movieId)) {
          is Success -> {
            onSuccess(result.value.trailers)
          }
          is ErrorResult -> {
            onError(result.message)
          }
        }
      }
    }
  }

  private fun onSuccess(trailers: List<Trailer>) {
    var keyVideo = ""
    for (trailer in trailers) {
      if (trailer.site?.lowercase(Locale.ROOT)
          ?.trim() == "youtube"
      ) {
        keyVideo = trailer.key ?: ""
        break
      }
    }

    if (keyVideo.isNotEmpty()) {
      movieTrailerView?.showTrailer(keyVideo)
    }
  }

  private fun onError(message: String) {
    movieTrailerView?.showErrorMessage(message)
  }

  override fun onDestroy() {
    if (scope.isActive) {
      scope.cancel()
    }

    this.movieTrailerView = null
    this.movieTrailerModel = null
  }
}
