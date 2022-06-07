package com.dpfht.tmdbmvp.data.model.remote.response

import com.dpfht.tmdbmvp.data.model.remote.Trailer

data class TrailerResponse(
    val id: Int = 0,
    val results: ArrayList<Trailer>? = null
)
