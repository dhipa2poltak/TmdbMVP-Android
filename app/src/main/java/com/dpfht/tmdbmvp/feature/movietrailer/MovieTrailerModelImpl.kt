package com.dpfht.tmdbmvp.feature.movietrailer

import com.dpfht.tmdbmvp.data.api.CallbackWrapper
import com.dpfht.tmdbmvp.data.model.remote.Trailer
import com.dpfht.tmdbmvp.data.model.remote.response.TrailerResponse
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieTrailerModelImpl(
  private val appRepository: AppRepository,
  private val compositeDisposable: CompositeDisposable
): MovieTrailerModel {

  override fun getMovieTrailer(
    movieId: Int,
    onSuccess: (List<Trailer>) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    val subs = appRepository.getMovieTrailer(movieId)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeWith(object : CallbackWrapper<TrailerResponse?>() {
        override fun onSuccessCall(responseBody: TrailerResponse?) {
          responseBody?.results?.let {
            onSuccess(it)
          }
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

  override fun onDestroy() {
    compositeDisposable.dispose()
  }
}
