package com.dpfht.tmdbmvp.feature.genre

import androidx.navigation.NavDirections
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreModel
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenrePresenter
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreView
import com.dpfht.tmdbmvp.data.model.remote.Genre
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper.ErrorResult
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper.Success
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class GenrePresenterImpl(
  private var genreView: GenreView? = null,
  private var genreModel: GenreModel? = null,
  private val genres: ArrayList<Genre>,
  private val scope: CoroutineScope
): GenrePresenter {

  override fun start() {
    if (genres.isEmpty()) {
      getMovieGenre()
    }
  }

  private fun getMovieGenre() {
    genreView?.showLoadingDialog()
    scope.launch(Dispatchers.Main) {
      genreModel?.let {
        when (val result = it.getMovieGenre()) {
          is Success -> {
            onSuccess(result.value.genres)
          }
          is ErrorResult -> {
            onError(result.message)
          }
        }
      }
    }
  }

  private fun onSuccess(genres: List<Genre>) {
    for (genre in genres) {
      this.genres.add(genre)
      genreView?.notifyItemInserted(this.genres.size - 1)
    }
    genreView?.hideLoadingDialog()
  }

  private fun onError(message: String) {
    genreView?.hideLoadingDialog()
    genreView?.showErrorMessage(message)
  }

  override fun getNavDirectionsOnClickGenreAt(position: Int): NavDirections {
    val genre = genres[position]

    return GenreFragmentDirections.actionGenreFragmentToMoviesByGenreFragment(
      genre.id, genre.name ?: ""
    )
  }

  override fun onDestroy() {
    if (scope.isActive) {
      scope.cancel()
    }
    this.genreView = null
  }
}
