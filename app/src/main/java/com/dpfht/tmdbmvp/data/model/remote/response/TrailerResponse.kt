package com.dpfht.tmdbmvp.data.model.remote.response

import com.dpfht.tmdbmvp.data.model.remote.Trailer
import com.dpfht.tmdbmvp.domain.model.GetMovieTrailerResult

data class TrailerResponse(
    val id: Int = 0,
    val results: ArrayList<Trailer>? = null
)

fun TrailerResponse.toDomain() = GetMovieTrailerResult(this.results ?: arrayListOf())
