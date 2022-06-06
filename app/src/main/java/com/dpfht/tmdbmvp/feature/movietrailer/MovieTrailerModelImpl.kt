package com.dpfht.tmdbmvp.feature.movietrailer

import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.domain.model.GetMovieTrailerResult
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerModel

class MovieTrailerModelImpl(
  private val appRepository: AppRepository
): MovieTrailerModel {

  override suspend fun getMovieTrailer(movieId: Int): ModelResultWrapper<GetMovieTrailerResult> {
    return appRepository.getMovieTrailer(movieId)
  }
}
