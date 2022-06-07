package com.dpfht.tmdbmvp.feature.movietrailer

import com.dpfht.tmdbmvp.data.repository.AppRepository
import com.dpfht.tmdbmvp.domain.model.GetMovieTrailerResult
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerModel
import io.reactivex.Observable

class MovieTrailerModelImpl(
  private val appRepository: AppRepository
): MovieTrailerModel {

  override fun getMovieTrailer(movieId: Int): Observable<GetMovieTrailerResult> {
    return appRepository.getMovieTrailer(movieId)
  }
}
