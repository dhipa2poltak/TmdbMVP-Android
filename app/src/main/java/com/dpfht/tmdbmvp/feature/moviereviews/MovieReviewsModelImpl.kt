package com.dpfht.tmdbmvp.feature.moviereviews

import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.domain.model.GetMovieReviewResult
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsModel

class MovieReviewsModelImpl(
  private val appRepository: AppRepository
): MovieReviewsModel {

  override suspend fun getMovieReviews(movieId: Int, page: Int): ModelResultWrapper<GetMovieReviewResult> {
    return appRepository.getMovieReviews(movieId, page)
  }
}
