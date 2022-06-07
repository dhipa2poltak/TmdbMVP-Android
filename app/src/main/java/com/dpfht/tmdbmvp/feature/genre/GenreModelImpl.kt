package com.dpfht.tmdbmvp.feature.genre

import com.dpfht.tmdbmvp.data.api.CallbackWrapper
import com.dpfht.tmdbmvp.data.model.remote.Genre
import com.dpfht.tmdbmvp.data.model.remote.response.GenreResponse
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class GenreModelImpl(
  private val appRepository: AppRepository,
  private val compositeDisposable: CompositeDisposable
): GenreModel {

  override fun getMovieGenre(
    onSuccess: (List<Genre>) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    val subs = appRepository.getMovieGenre()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeWith(object : CallbackWrapper<Response<GenreResponse?>, GenreResponse?>() {
        override fun onSuccessCall(responseBody: GenreResponse?) {
          responseBody?.genres?.let {
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
