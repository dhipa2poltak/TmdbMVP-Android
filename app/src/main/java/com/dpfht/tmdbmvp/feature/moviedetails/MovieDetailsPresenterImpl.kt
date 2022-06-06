package com.dpfht.tmdbmvp.feature.moviedetails

import androidx.navigation.NavDirections
import com.dpfht.tmdbmvp.Config
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper.ErrorResult
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper.Success
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsModel
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsPresenter
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MovieDetailsPresenterImpl(
  private var movieDetailsView: MovieDetailsView? = null,
  private var movieDetailsModel: MovieDetailsModel? = null,
  private val scope: CoroutineScope
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
    scope.launch(Dispatchers.Main) {
      movieDetailsModel?.let {
        when (val result = it.getMovieDetails(_movieId)) {
          is Success -> {
            onSuccess(result.value.movieId, result.value.title, result.value.overview, result.value.posterPath)
          }
          is ErrorResult -> {
            onError(result.message)
          }
        }
      }
    }
  }

  private fun onSuccess(pMovieId: Int, pTitle: String, pOverview: String, pPosterPath: String) {
    imageUrl = ""
    if (pPosterPath.isNotEmpty()) {
      imageUrl = Config.IMAGE_URL_BASE_PATH + pPosterPath
    }

    _movieId = pMovieId
    title = pTitle
    overview = pOverview
    movieDetailsView?.showMovieDetails(
      title,
      overview,
      imageUrl)

    movieDetailsView?.hideLoadingDialog()
  }

  private fun onError(message: String) {
    movieDetailsView?.hideLoadingDialog()
    movieDetailsView?.showErrorMessage(message)
  }

  override fun getNavDirectionsToMovieReviews(): NavDirections {
    return MovieDetailsFragmentDirections.actionMovieDetailsToMovieReviews(_movieId, title)
  }

  override fun onDestroy() {
    if (scope.isActive) {
      scope.cancel()
    }
    this.movieDetailsView = null
  }
}
