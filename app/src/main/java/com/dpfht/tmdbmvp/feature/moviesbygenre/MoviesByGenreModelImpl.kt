package com.dpfht.tmdbmvp.feature.moviesbygenre

import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreModel
import io.reactivex.Observable

class MoviesByGenreModelImpl(
  private val appRepository: AppRepository
): MoviesByGenreModel {

  override fun getMoviesByGenre(genreId: Int, page: Int): Observable<GetMovieByGenreResult> {
    return appRepository.getMoviesByGenre(genreId.toString(), page)
  }
}
