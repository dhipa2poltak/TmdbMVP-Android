package com.dpfht.tmdbmvp.feature.moviesbygenre

import androidx.navigation.NavDirections
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreModel
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenrePresenter
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreView
import com.dpfht.tmdbmvp.data.model.Movie

class MoviesByGenrePresenterImpl(
  private var moviesByGenreView: MoviesByGenreView? = null,
  private var moviesByGenreModel: MoviesByGenreModel? = null
): MoviesByGenrePresenter {

  override val movies = ArrayList<Movie>()

  private var genreId = -1
  var page = 0

  override var isLoadingData = false

  override fun setGenreIdValue(genreId: Int) {
    this.genreId = genreId
  }

  override fun getMoviesByGenre() {
    moviesByGenreView?.showLoadingDialog()
    isLoadingData = true
    moviesByGenreModel?.getMoviesByGenre(
      genreId, page + 1, this::onSuccess, this::onError, this::onCancel
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
    isLoadingData = false
  }

  fun onError(message: String) {
    moviesByGenreView?.hideLoadingDialog()
    isLoadingData = false
    moviesByGenreView?.showErrorMessage(message)
  }

  fun onCancel() {
    moviesByGenreView?.hideLoadingDialog()
    isLoadingData = false
    moviesByGenreView?.showCanceledMessage()
  }

  override fun getNavDirectionsOnClickMovieAt(position: Int): NavDirections {
    val movie = movies[position]

    return MoviesByGenreFragmentDirections.actionMovieByGenreToMovieDetails(movie.id)
  }

  override fun onDestroy() {
    this.moviesByGenreView = null
    this.moviesByGenreModel?.onDestroy()
    this.moviesByGenreModel = null
  }
}
