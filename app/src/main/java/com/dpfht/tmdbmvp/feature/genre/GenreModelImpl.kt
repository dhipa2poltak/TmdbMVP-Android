package com.dpfht.tmdbmvp.feature.genre

import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.domain.model.GetMovieGenreResult
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreModel
import io.reactivex.Observable

class GenreModelImpl(
  private val appRepository: AppRepository
): GenreModel {

  override fun getMovieGenre(): Observable<GetMovieGenreResult> {
    return appRepository.getMovieGenre()
  }
}
