package com.dpfht.tmdbmvp.feature.moviedetails

import androidx.navigation.NavDirections
import com.dpfht.tmdbmvp.Config
import com.dpfht.tmdbmvp.data.model.response.MovieDetailsResponse
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsModel
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsPresenter
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsView

class MovieDetailsPresenterImpl(
  private var movieDetailsView: MovieDetailsView? = null,
  private var movieDetailsModel: MovieDetailsModel? = null
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
    movieDetailsModel?.getMovieDetails(
      _movieId, this::onSuccess, this::onError, this::onCancel
    )
  }

  fun onSuccess(response: MovieDetailsResponse) {
    imageUrl = ""
    if (response.posterPath != null) {
      imageUrl = Config.IMAGE_URL_BASE_PATH + response.posterPath
    }

    _movieId = response.id
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

  override fun getNavDirectionsToMovieReviews(): NavDirections {
    return MovieDetailsFragmentDirections.actionMovieDetailsToMovieReviews(_movieId, title)
  }

  override fun onDestroy() {
    this.movieDetailsView = null
  }
}
