package com.dpfht.tmdbmvp.data.repository

import com.dpfht.tmdbmvp.data.api.RestService
import com.dpfht.tmdbmvp.data.api.ResultWrapper
import com.dpfht.tmdbmvp.data.model.remote.response.DiscoverMovieByGenreResponse
import com.dpfht.tmdbmvp.data.model.remote.response.GenreResponse
import com.dpfht.tmdbmvp.data.model.remote.response.MovieDetailsResponse
import com.dpfht.tmdbmvp.data.model.remote.response.ReviewResponse
import com.dpfht.tmdbmvp.data.model.remote.response.TrailerResponse
import com.dpfht.tmdbmvp.util.safeApiCall
import kotlinx.coroutines.Dispatchers

class AppRepositoryImpl(private val restService: RestService): AppRepository {

  override suspend fun getMovieGenre(): ResultWrapper<GenreResponse> {
    return safeApiCall(Dispatchers.IO) { restService.getMovieGenre() }
  }

  override suspend fun getMoviesByGenre(genreId: String, page: Int): ResultWrapper<DiscoverMovieByGenreResponse> {
    return safeApiCall(Dispatchers.IO) { restService.getMoviesByGenre(genreId, page) }
  }

  override suspend fun getMovieDetail(movieId: Int): ResultWrapper<MovieDetailsResponse> {
    return safeApiCall(Dispatchers.IO) { restService.getMovieDetail(movieId) }
  }

  override suspend fun getMovieReviews(movieId: Int, page: Int): ResultWrapper<ReviewResponse> {
    return safeApiCall(Dispatchers.IO) { restService.getMovieReviews(movieId, page) }
  }

  override suspend fun getMovieTrailer(movieId: Int): ResultWrapper<TrailerResponse> {
    return safeApiCall(Dispatchers.IO) { restService.getMovieTrailers(movieId) }
  }
}
