package com.dpfht.tmdbmvp.repository

import com.dpfht.tmdbmvp.model.response.DiscoverMovieByGenreResponse
import com.dpfht.tmdbmvp.model.response.GenreResponse
import com.dpfht.tmdbmvp.model.response.MovieDetailsResponse
import com.dpfht.tmdbmvp.model.response.ReviewResponse
import com.dpfht.tmdbmvp.model.response.TrailerResponse
import com.dpfht.tmdbmvp.rest.RestService
import retrofit2.Call

class AppRepositoryImpl(private val restService: RestService): AppRepository {

  override fun getMovieGenre():  Call<GenreResponse?> {
    return restService.getMovieGenre()
  }

  override fun getMoviesByGenre(genreId: String, page: Int): Call<DiscoverMovieByGenreResponse?> {
    return restService.getMoviesByGenre(genreId, page)
  }

  override fun getMovieDetail(movieId: Int): Call<MovieDetailsResponse?> {
    return restService.getMovieDetail(movieId)
  }

  override fun getMovieReviews(movieId: Int, page: Int): Call<ReviewResponse?> {
    return restService.getMovieReviews(movieId, page)
  }

  override fun getMovieTrailer(movieId: Int): Call<TrailerResponse?> {
    return restService.getMovieTrailers(movieId)
  }
}
