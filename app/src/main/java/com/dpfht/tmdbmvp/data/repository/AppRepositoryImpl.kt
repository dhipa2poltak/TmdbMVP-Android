package com.dpfht.tmdbmvp.data.repository

import com.dpfht.tmdbmvp.data.api.RestService
import com.dpfht.tmdbmvp.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbmvp.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbmvp.domain.model.GetMovieGenreResult
import com.dpfht.tmdbmvp.domain.model.GetMovieReviewResult
import com.dpfht.tmdbmvp.domain.model.GetMovieTrailerResult
import io.reactivex.Observable

class AppRepositoryImpl(private val restService: RestService): AppRepository {

  override fun getMovieGenre():  Observable<GetMovieGenreResult> {
    return restService.getMovieGenre()
      .map { (GetMovieGenreResult(it.genres ?: arrayListOf())) }
  }

  override fun getMoviesByGenre(genreId: String, page: Int): Observable<GetMovieByGenreResult> {
    return restService.getMoviesByGenre(genreId, page)
      .map { GetMovieByGenreResult(it.results ?: arrayListOf(), it.page) }
  }

  override fun getMovieDetail(movieId: Int): Observable<GetMovieDetailsResult> {
    return restService.getMovieDetail(movieId)
      .map { GetMovieDetailsResult(it.id, it.title ?: "", it.overview ?: "", it.posterPath ?: "") }
  }

  override fun getMovieReviews(movieId: Int, page: Int): Observable<GetMovieReviewResult> {
    return restService.getMovieReviews(movieId, page)
      .map { GetMovieReviewResult(it.results ?: arrayListOf(), it.page) }
  }

  override fun getMovieTrailer(movieId: Int): Observable<GetMovieTrailerResult> {
    return restService.getMovieTrailers(movieId)
      .map { GetMovieTrailerResult(it.results ?: arrayListOf()) }
  }
}
