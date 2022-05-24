package com.dpfht.tmdbmvp.repository

import com.dpfht.tmdbmvp.model.response.DiscoverMovieByGenreResponse
import com.dpfht.tmdbmvp.model.response.GenreResponse
import com.dpfht.tmdbmvp.model.response.MovieDetailsResponse
import com.dpfht.tmdbmvp.model.response.ReviewResponse
import com.dpfht.tmdbmvp.model.response.TrailerResponse
import com.dpfht.tmdbmvp.rest.RestService
import io.reactivex.Observable
import retrofit2.Response

class AppRepositoryImpl(private val restService: RestService): AppRepository {

  override fun getMovieGenre():  Observable<Response<GenreResponse?>> {
    return restService.getMovieGenre()
  }

  override fun getMoviesByGenre(genreId: String, page: Int): Observable<Response<DiscoverMovieByGenreResponse?>> {
    return restService.getMoviesByGenre(genreId, page)
  }

  override fun getMovieDetail(movieId: Int): Observable<Response<MovieDetailsResponse?>> {
    return restService.getMovieDetail(movieId)
  }

  override fun getMovieReviews(movieId: Int, page: Int): Observable<Response<ReviewResponse?>> {
    return restService.getMovieReviews(movieId, page)
  }

  override fun getMovieTrailer(movieId: Int): Observable<Response<TrailerResponse?>> {
    return restService.getMovieTrailers(movieId)
  }
}
