package com.dpfht.tmdbmvp.feature.moviedetails

import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsModel

class MovieDetailsModelImpl(
  private val appRepository: AppRepository
): MovieDetailsModel {

  override suspend fun getMovieDetails(movieId: Int): ModelResultWrapper<GetMovieDetailsResult> {
    return appRepository.getMovieDetail(movieId)
  }
}
