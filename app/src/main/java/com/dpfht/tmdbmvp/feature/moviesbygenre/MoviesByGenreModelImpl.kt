package com.dpfht.tmdbmvp.feature.moviesbygenre

import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreModel
import com.dpfht.tmdbmvp.data.model.Movie
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.util.ErrorUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class MoviesByGenreModelImpl(
  val appRepository: AppRepository
): MoviesByGenreModel {

  private val compositeDisposable = CompositeDisposable()

  override fun getMoviesByGenre(
    genreId: Int,
    page: Int,
    onSuccess: (List<Movie>, Int) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    val subs = appRepository.getMoviesByGenre(genreId.toString(), page)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({ response ->
        if (response.isSuccessful) {
          response.body()?.let { resp ->
            resp.results?.let {
              onSuccess(it, resp.page)
            }
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
