package com.dpfht.tmdbmvp.data.repository

import com.dpfht.tmdbmvp.data.api.ResultWrapper
import com.dpfht.tmdbmvp.data.model.remote.response.DiscoverMovieByGenreResponse
import com.dpfht.tmdbmvp.data.model.remote.response.GenreResponse
import com.dpfht.tmdbmvp.data.model.remote.response.MovieDetailsResponse
import com.dpfht.tmdbmvp.data.model.remote.response.ReviewResponse
import com.dpfht.tmdbmvp.data.model.remote.response.TrailerResponse

interface AppRepository {

  suspend fun getMovieGenre(): ResultWrapper<GenreResponse>

  suspend fun getMoviesByGenre(genreId: String, page: Int): ResultWrapper<DiscoverMovieByGenreResponse>

  suspend fun getMovieDetail(movieId: Int): ResultWrapper<MovieDetailsResponse>

  suspend fun getMovieReviews(movieId: Int, page: Int): ResultWrapper<ReviewResponse>

  suspend fun getMovieTrailer(movieId: Int): ResultWrapper<TrailerResponse>
}
