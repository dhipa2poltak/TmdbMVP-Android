package com.dpfht.tmdbmvp.feature.genre

import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.domain.model.GetMovieGenreResult
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreModel

class GenreModelImpl(
  private val appRepository: AppRepository
): GenreModel {

  override suspend fun getMovieGenre(): ModelResultWrapper<GetMovieGenreResult> {
    return appRepository.getMovieGenre()
  }
}
