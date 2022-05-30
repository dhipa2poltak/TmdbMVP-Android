package com.dpfht.tmdbmvp.feature.moviedetails

import com.dpfht.tmdbmvp.data.api.CallbackWrapper
import com.dpfht.tmdbmvp.data.model.response.MovieDetailsResponse
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsModelImpl(
  private val appRepository: AppRepository,
  private val compositeDisposable: CompositeDisposable
): MovieDetailsModel {

  override fun getMovieDetails(
    movieId: Int,
    onSuccess: (MovieDetailsResponse) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    val subs = appRepository.getMovieDetail(movieId)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeWith(object : CallbackWrapper<MovieDetailsResponse?>() {
        override fun onSuccessCall(responseBody: MovieDetailsResponse?) {
          responseBody?.let {
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
