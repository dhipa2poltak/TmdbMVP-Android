package com.dpfht.tmdbmvp.feature.moviedetails

import com.dpfht.tmdbmvp.Config
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsModel
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsPresenter
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsView
import com.dpfht.tmdbmvp.data.model.response.MovieDetailsResponse

class MovieDetailsPresenterImpl(
  private var movieDetailsView: MovieDetailsView? = null,
  private var movieDetailsModel: MovieDetailsModel? = null
): MovieDetailsPresenter {

  override var movieId = -1
  override var title = ""
  override var overview = ""
  override var imageUrl = ""

  override fun getMovieDetails(movieId: Int) {
    movieDetailsView?.showLoadingDialog()
    movieDetailsModel?.getMovieDetails(
      movieId, this::onSuccess, this::onError, this::onCancel
    )
  }

  fun onSuccess(response: MovieDetailsResponse) {
    imageUrl = ""
    if (response.posterPath != null) {
      imageUrl = Config.IMAGE_URL_BASE_PATH + response.posterPath
    }

    movieId = response.id
    title = response.title ?: ""
    overview = response.overview ?: ""
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

  override fun onDestroy() {
    this.movieDetailsView = null
  }
}
