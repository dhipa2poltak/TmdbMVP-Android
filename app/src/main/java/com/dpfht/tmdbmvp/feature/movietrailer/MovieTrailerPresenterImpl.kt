package com.dpfht.tmdbmvp.feature.movietrailer

import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerModel
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerPresenter
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerView
import com.dpfht.tmdbmvp.model.Trailer
import java.util.Locale

class MovieTrailerPresenterImpl(
  private var movieTrailerView: MovieTrailerView? = null,
  private var movieTrailerModel: MovieTrailerModel? = null
): MovieTrailerPresenter {

  override fun getMovieTrailer(movieId: Int) {
    movieTrailerModel?.getMovieTrailer(
      movieId, this::onSuccess, this::onError, this::onCancel
    )
  }

  fun onSuccess(trailers: List<Trailer>) {
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

  fun onError(message: String) {
    movieTrailerView?.showErrorMessage(message)
  }

  fun onCancel() {
    movieTrailerView?.showCanceledMessage()
  }

  override fun onDestroy() {
    this.movieTrailerView = null
  }
}
