package com.dpfht.tmdbmvp.feature.moviesbygenre

import androidx.navigation.NavDirections
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreModel
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenrePresenter
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreView
import com.dpfht.tmdbmvp.data.model.remote.Movie
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper.ErrorResult
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper.Success
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MoviesByGenrePresenterImpl(
  private var moviesByGenreView: MoviesByGenreView? = null,
  private var moviesByGenreModel: MoviesByGenreModel? = null,
  private val movies: ArrayList<Movie>,
  private val scope: CoroutineScope
): MoviesByGenrePresenter {

  private var _genreId = -1
  private var page = 0
  private var _isLoadingData = false
  private var isNextEmptyDataResponse = false

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
    if (isNextEmptyDataResponse) return

    scope.launch(Dispatchers.Main) {
      moviesByGenreView?.showLoadingDialog()
      _isLoadingData = true

      moviesByGenreModel?.let {
        when (val result = it.getMoviesByGenre(_genreId, page + 1)) {
          is Success -> {
            onSuccess(result.value.movies, result.value.page)
          }
          is ErrorResult -> {
            onError(result.message)
          }
        }
      }
    }
  }

  private fun onSuccess(movies: List<Movie>, page: Int) {
    if (movies.isNotEmpty()) {
      this.page = page

      for (movie in movies) {
        this.movies.add(movie)
        moviesByGenreView?.notifyItemInserted(this.movies.size - 1)
      }
    } else {
      isNextEmptyDataResponse = true
    }

    moviesByGenreView?.hideLoadingDialog()
    _isLoadingData = false
  }

  private fun onError(message: String) {
    moviesByGenreView?.hideLoadingDialog()
    _isLoadingData = false
    moviesByGenreView?.showErrorMessage(message)
  }

  override fun getNavDirectionsOnClickMovieAt(position: Int): NavDirections {
    val movie = movies[position]

    return MoviesByGenreFragmentDirections.actionMovieByGenreToMovieDetails(movie.id)
  }

  override fun onDestroy() {
    if (scope.isActive) {
      scope.cancel()
    }
    this.moviesByGenreView = null
  }
}
