package com.dpfht.tmdbmvp.data.repository

import com.dpfht.tmdbmvp.data.model.response.DiscoverMovieByGenreResponse
import com.dpfht.tmdbmvp.data.model.response.GenreResponse
import com.dpfht.tmdbmvp.data.model.response.MovieDetailsResponse
import com.dpfht.tmdbmvp.data.model.response.ReviewResponse
import com.dpfht.tmdbmvp.data.model.response.TrailerResponse
import io.reactivex.Observable
import retrofit2.Response

interface AppRepository {

  fun getMovieGenre():  Observable<Response<GenreResponse?>>

  fun getMoviesByGenre(genreId: String, page: Int): Observable<Response<DiscoverMovieByGenreResponse?>>

  fun getMovieDetail(movieId: Int): Observable<Response<MovieDetailsResponse?>>

  fun getMovieReviews(movieId: Int, page: Int): Observable<Response<ReviewResponse?>>

  fun getMovieTrailer(movieId: Int): Observable<Response<TrailerResponse?>>
}
