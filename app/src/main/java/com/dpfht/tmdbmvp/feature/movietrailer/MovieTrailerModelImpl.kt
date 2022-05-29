package com.dpfht.tmdbmvp.feature.movietrailer

import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerModel
import com.dpfht.tmdbmvp.data.model.Trailer
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.util.ErrorUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class MovieTrailerModelImpl(
  val appRepository: AppRepository
): MovieTrailerModel {

  private val compositeDisposable = CompositeDisposable()

  override fun getMovieTrailer(
    movieId: Int,
    onSuccess: (List<Trailer>) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    val subs = appRepository.getMovieTrailer(movieId)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({ response ->
        if (response.isSuccessful) {
          response.body()?.results?.let {
            onSuccess(it)
          }
        } else {
          val errorResponse = ErrorUtil.parseApiError(response)

          onError(errorResponse.statusMessage ?: "")
        }
      }, { t ->
        if (t is IOException) {
          onError("error in connection")
        } else {
          onError("error in conversion")
        }
      })

    compositeDisposable.add(subs)
  }

  override fun onDestroy() {
    compositeDisposable.dispose()
  }
}
