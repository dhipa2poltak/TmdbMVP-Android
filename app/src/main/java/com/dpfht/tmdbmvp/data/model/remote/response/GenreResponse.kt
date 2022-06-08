package com.dpfht.tmdbmvp.data.model.remote.response

import com.dpfht.tmdbmvp.data.model.remote.Genre
import com.dpfht.tmdbmvp.domain.model.GetMovieGenreResult

data class GenreResponse(
    val genres: List<Genre>? = null
)

fun GenreResponse.toDomain() = GetMovieGenreResult(this.genres ?: arrayListOf())

