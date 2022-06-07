package com.dpfht.tmdbmvp.feature.moviereviews

import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.domain.model.GetMovieReviewResult
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsModel
import io.reactivex.Observable

class MovieReviewsModelImpl(
  private val appRepository: AppRepository
): MovieReviewsModel {

  override fun getMovieReviews(movieId: Int, page: Int): Observable<GetMovieReviewResult> {
    return appRepository.getMovieReviews(movieId, page)
  }
}
