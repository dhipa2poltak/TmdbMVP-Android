package com.dpfht.tmdbmvp.feature.moviesbygenre

import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreModel

class MoviesByGenreModelImpl(
  private val appRepository: AppRepository
): MoviesByGenreModel {

  override suspend fun getMoviesByGenre(genreId: Int, page: Int): ModelResultWrapper<GetMovieByGenreResult> {
    return appRepository.getMoviesByGenre(genreId.toString(), page)
  }
}
