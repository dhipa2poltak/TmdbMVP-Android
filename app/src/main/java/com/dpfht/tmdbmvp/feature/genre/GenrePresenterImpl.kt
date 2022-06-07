package com.dpfht.tmdbmvp.feature.genre

import androidx.navigation.NavDirections
import com.dpfht.tmdbmvp.data.api.CallbackWrapper
import com.dpfht.tmdbmvp.data.model.remote.Genre
import com.dpfht.tmdbmvp.domain.model.GetMovieGenreResult
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreModel
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenrePresenter
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GenrePresenterImpl(
  private var genreView: GenreView? = null,
  private var genreModel: GenreModel? = null,
  private val genres: ArrayList<Genre>,
  private val compositeDisposable: CompositeDisposable
): GenrePresenter {

  override fun start() {
    if (genres.isEmpty()) {
      getMovieGenre()
    }
  }

  private fun getMovieGenre() {
    genreView?.showLoadingDialog()

    genreModel?.let { model ->
      val subs = model.getMovieGenre()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(object : CallbackWrapper<GetMovieGenreResult>() {
          override fun onSuccessCall(result: GetMovieGenreResult) {
            onSuccess(result.genres)
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

  fun onSuccess(genres: List<Genre>) {
    for (genre in genres) {
      this.genres.add(genre)
      genreView?.notifyItemInserted(this.genres.size - 1)
    }
    genreView?.hideLoadingDialog()
  }

  fun onError(message: String) {
    genreView?.hideLoadingDialog()
    genreView?.showErrorMessage(message)
  }

  fun onCancel() {
    genreView?.hideLoadingDialog()
    genreView?.showCanceledMessage()
  }

  override fun getNavDirectionsOnClickGenreAt(position: Int): NavDirections {
    val genre = genres[position]

    return GenreFragmentDirections.actionGenreFragmentToMoviesByGenreFragment(
      genre.id, genre.name ?: ""
    )
  }

  override fun onDestroy() {
    if (!compositeDisposable.isDisposed) {
      compositeDisposable.dispose()
    }
    this.genreView = null
    this.genreModel = null
  }
}
