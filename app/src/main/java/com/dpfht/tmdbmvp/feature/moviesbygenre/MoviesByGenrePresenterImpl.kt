package com.dpfht.tmdbmvp.feature.moviesbygenre

import androidx.navigation.NavDirections
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreModel
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenrePresenter
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreView
import com.dpfht.tmdbmvp.data.model.remote.Movie

class MoviesByGenrePresenterImpl(
  private var moviesByGenreView: MoviesByGenreView? = null,
  private var moviesByGenreModel: MoviesByGenreModel? = null,
  private val movies: ArrayList<Movie>
): MoviesByGenrePresenter {

  private var _genreId = -1
  private var page = 0
  private var _isLoadingData = false

  override fun start() {
    if (_genreId != -1 && movies.isEmpty()) {
      getMoviesByGenre()
    }
  }

  override fun isLoadingData() = _isLoadingData

  override fun setGenreId(genreId: Int) {
    this._genreId = genreId
  }

  override fun getMoviesByGenre() {
    moviesByGenreView?.showLoadingDialog()
    _isLoadingData = true
    moviesByGenreModel?.getMoviesByGenre(
      _genreId, page + 1, this::onSuccess, this::onError, this::onCancel
    )
  }

  fun onSuccess(movies: List<Movie>, page: Int) {
    if (movies.isNotEmpty()) {
      this.page = page

      for (movie in movies) {
        this.movies.add(movie)
        moviesByGenreView?.notifyItemInserted(this.movies.size - 1)
      }
    }

    moviesByGenreView?.hideLoadingDialog()
    _isLoadingData = false
  }

  fun onError(message: String) {
    moviesByGenreView?.hideLoadingDialog()
    _isLoadingData = false
    moviesByGenreView?.showErrorMessage(message)
  }

  fun onCancel() {
    moviesByGenreView?.hideLoadingDialog()
    _isLoadingData = false
    moviesByGenreView?.showCanceledMessage()
  }

  override fun getNavDirectionsOnClickMovieAt(position: Int): NavDirections {
    val movie = movies[position]

    return MoviesByGenreFragmentDirections.actionMovieByGenreToMovieDetails(movie.id)
  }

  override fun onDestroy() {
    this.moviesByGenreView = null
  }
}
