package com.dpfht.tmdbmvp.feature.genre

import androidx.navigation.NavDirections
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreModel
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenrePresenter
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreView
import com.dpfht.tmdbmvp.data.model.Genre

class GenrePresenterImpl(
  private var genreView: GenreView? = null,
  private var genreModel: GenreModel? = null,
  private val genres: ArrayList<Genre>
): GenrePresenter {

  override fun start() {
    if (genres.isEmpty()) {
      getMovieGenre()
    }
  }

  private fun getMovieGenre() {
    genreView?.showLoadingDialog()
    genreModel?.getMovieGenre(this::onSuccess, this::onError, this::onCancel)
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
    this.genreView = null
  }
}
