package com.dpfht.tmdbmvp.feature.moviereviews

import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsModel
import com.dpfht.tmdbmvp.data.model.Review
import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.util.ErrorUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class MovieReviewsModelImpl(
  val appRepository: AppRepository
): MovieReviewsModel {

  private val compositeDisposable = CompositeDisposable()

  override fun getMovieReviews(
    movieId: Int,
    page: Int,
    onSuccess: (List<Review>, Int) -> Unit,
    onError: (String) -> Unit,
    onCancel: () -> Unit
  ) {
    val subs = appRepository.getMovieReviews(movieId, page)
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
