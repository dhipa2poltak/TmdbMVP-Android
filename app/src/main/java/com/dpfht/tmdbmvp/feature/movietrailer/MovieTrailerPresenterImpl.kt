package com.dpfht.tmdbmvp.feature.movietrailer

import com.dpfht.tmdbmvp.data.api.CallbackWrapper
import com.dpfht.tmdbmvp.data.model.remote.Trailer
import com.dpfht.tmdbmvp.domain.model.GetMovieTrailerResult
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerModel
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerPresenter
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.Locale

class MovieTrailerPresenterImpl(
  private var movieTrailerView: MovieTrailerView? = null,
  private var movieTrailerModel: MovieTrailerModel? = null,
  private val compositeDisposable: CompositeDisposable
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
    movieTrailerModel?.let { model ->
      val subs = model.getMovieTrailer(_movieId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(object : CallbackWrapper<GetMovieTrailerResult>() {
          override fun onSuccessCall(result: GetMovieTrailerResult) {
            onSuccess(result.trailers)
          }

          override fun onErrorCall(message: String) {
            onError(message)
          }

          override fun onCancelCall() {
            onCancel()
          }
        })

      compositeDisposable.add(subs)
    }
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
    if (!compositeDisposable.isDisposed) {
      compositeDisposable.dispose()
    }
    this.movieTrailerView = null
    this.movieTrailerModel = null
  }
}
