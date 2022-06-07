package com.dpfht.tmdbmvp.data.repository

import com.dpfht.tmdbmvp.data.api.RestService
import com.dpfht.tmdbmvp.data.model.remote.response.DiscoverMovieByGenreResponse
import com.dpfht.tmdbmvp.data.model.remote.response.GenreResponse
import com.dpfht.tmdbmvp.data.model.remote.response.MovieDetailsResponse
import com.dpfht.tmdbmvp.data.model.remote.response.ReviewResponse
import com.dpfht.tmdbmvp.data.model.remote.response.TrailerResponse
import io.reactivex.Observable

class AppRepositoryImpl(private val restService: RestService): AppRepository {

  override fun getMovieGenre():  Observable<GenreResponse> {
    return restService.getMovieGenre()
  }

  override fun getMoviesByGenre(genreId: String, page: Int): Observable<DiscoverMovieByGenreResponse> {
    return restService.getMoviesByGenre(genreId, page)
  }

  override fun getMovieDetail(movieId: Int): Observable<MovieDetailsResponse> {
    return restService.getMovieDetail(movieId)
  }

  override fun getMovieReviews(movieId: Int, page: Int): Observable<ReviewResponse> {
    return restService.getMovieReviews(movieId, page)
  }

  override fun getMovieTrailer(movieId: Int): Observable<TrailerResponse> {
    return restService.getMovieTrailers(movieId)
  }
}
