package com.dpfht.tmdbmvp.data.api

import com.dpfht.tmdbmvp.data.model.remote.response.DiscoverMovieByGenreResponse
import com.dpfht.tmdbmvp.data.model.remote.response.GenreResponse
import com.dpfht.tmdbmvp.data.model.remote.response.MovieDetailsResponse
import com.dpfht.tmdbmvp.data.model.remote.response.ReviewResponse
import com.dpfht.tmdbmvp.data.model.remote.response.TrailerResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestService {

    @GET("genre/movie/list")
    fun getMovieGenre():  Observable<Response<GenreResponse>>

    @GET("discover/movie")
    fun getMoviesByGenre(
        @Query("with_genres") genreId: String,
        @Query("page") page: Int): Observable<Response<DiscoverMovieByGenreResponse>>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: Int): Observable<Response<MovieDetailsResponse>>

    @GET("movie/{movie_id}/reviews")
    fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
        @Query("language") language: String = "en-US"): Observable<Response<ReviewResponse>>

    @GET("movie/{movie_id}/videos")
    fun getMovieTrailers(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"): Observable<Response<TrailerResponse>>
}
