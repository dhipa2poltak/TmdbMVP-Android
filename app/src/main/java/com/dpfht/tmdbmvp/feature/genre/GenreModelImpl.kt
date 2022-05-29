package com.dpfht.tmdbmvp.feature.genre

import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreModel
import com.dpfht.tmdbmvp.data.model.Genre
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.util.ErrorUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class GenreModelImpl(
  val appRepository: AppRepository
): GenreModel {

  private val compositeDisposable = CompositeDisposable()

  override fun getMovieGenre(
    onSuccess: (List<Genre>) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    val subs = appRepository.getMovieGenre()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({ response ->
        if (response.isSuccessful) {
          response.body()?.genres?.let {
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
