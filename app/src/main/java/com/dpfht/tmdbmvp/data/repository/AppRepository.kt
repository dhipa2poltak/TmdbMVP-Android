package com.dpfht.tmdbmvp.data.repository

import com.dpfht.tmdbmvp.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbmvp.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbmvp.domain.model.GetMovieGenreResult
import com.dpfht.tmdbmvp.domain.model.GetMovieReviewResult
import com.dpfht.tmdbmvp.domain.model.GetMovieTrailerResult
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper

interface AppRepository {

  suspend fun getMovieGenre(): ModelResultWrapper<GetMovieGenreResult>

  suspend fun getMoviesByGenre(genreId: String, page: Int): ModelResultWrapper<GetMovieByGenreResult>

  suspend fun getMovieDetail(movieId: Int): ModelResultWrapper<GetMovieDetailsResult>

  suspend fun getMovieReviews(movieId: Int, page: Int): ModelResultWrapper<GetMovieReviewResult>

  suspend fun getMovieTrailer(movieId: Int): ModelResultWrapper<GetMovieTrailerResult>
}
