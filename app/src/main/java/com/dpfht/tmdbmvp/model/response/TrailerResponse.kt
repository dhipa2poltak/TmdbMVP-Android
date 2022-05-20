package com.dpfht.tmdbmvp.model.response

import com.dpfht.tmdbmvp.model.Trailer

data class TrailerResponse(
    var id: Int = 0,
    var results: ArrayList<Trailer>? = null
)
