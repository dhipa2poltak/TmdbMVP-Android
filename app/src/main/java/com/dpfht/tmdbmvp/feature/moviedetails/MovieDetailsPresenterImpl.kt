package com.dpfht.tmdbmvp.feature.moviedetails

import androidx.navigation.NavDirections
import com.dpfht.tmdbmvp.Config
import com.dpfht.tmdbmvp.data.api.CallbackWrapper
import com.dpfht.tmdbmvp.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsModel
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsPresenter
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsPresenterImpl(
  private var movieDetailsView: MovieDetailsView? = null,
  private var movieDetailsModel: MovieDetailsModel? = null,
  private val compositeDisposable: CompositeDisposable
): MovieDetailsPresenter {

  private var _movieId = -1
  private var title = ""
  private var overview = ""
  private var imageUrl = ""

  override fun setMovieId(movieId: Int) {
    this._movieId = movieId
  }

  override fun getMovieId(): Int {
    return _movieId
  }

  override fun start() {
    if (title.isEmpty()) {
      getMovieDetails()
    } else {
      movieDetailsView?.showMovieDetails(title, overview, imageUrl)
    }
  }

  private fun getMovieDetails() {
    movieDetailsView?.showLoadingDialog()

    movieDetailsModel?.let { model ->
      val subs = model.getMovieDetails(_movieId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(object : CallbackWrapper<GetMovieDetailsResult>() {
          override fun onSuccessCall(result: GetMovieDetailsResult) {
            onSuccess(result.movieId, result.title, result.overview, result.posterPath)
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

  fun onSuccess(pId: Int, pTitle: String, pOverview: String, pPosterPath: String) {
    imageUrl = ""
    if (pPosterPath.isNotEmpty()) {
      imageUrl = Config.IMAGE_URL_BASE_PATH + pPosterPath
    }

    _movieId = pId
    title = pTitle
    overview = pOverview
    movieDetailsView?.showMovieDetails(
      title,
      overview,
      imageUrl)

    movieDetailsView?.hideLoadingDialog()
  }

  fun onError(message: String) {
    movieDetailsView?.hideLoadingDialog()
    movieDetailsView?.showErrorMessage(message)
  }

  fun onCancel() {
    movieDetailsView?.hideLoadingDialog()
    movieDetailsView?.showCanceledMessage()
  }

  override fun getNavDirectionsToMovieReviews(): NavDirections {
    return MovieDetailsFragmentDirections.actionMovieDetailsToMovieReviews(_movieId, title)
  }

  override fun onDestroy() {
    if (!compositeDisposable.isDisposed) {
      compositeDisposable.dispose()
    }
    this.movieDetailsView = null
    this.movieDetailsModel = null
  }
}
