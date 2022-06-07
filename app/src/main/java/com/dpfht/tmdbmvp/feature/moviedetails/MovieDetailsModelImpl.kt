package com.dpfht.tmdbmvp.feature.moviedetails

import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsModel
import io.reactivex.Observable

class MovieDetailsModelImpl(
  private val appRepository: AppRepository
): MovieDetailsModel {

  override fun getMovieDetails(movieId: Int): Observable<GetMovieDetailsResult> {
    return appRepository.getMovieDetail(movieId)
  }
}
