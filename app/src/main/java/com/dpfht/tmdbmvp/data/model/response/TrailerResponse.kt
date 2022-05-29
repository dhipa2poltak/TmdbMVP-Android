package com.dpfht.tmdbmvp.data.model.response

import com.dpfht.tmdbmvp.data.model.Trailer

data class TrailerResponse(
    var id: Int = 0,
    var results: ArrayList<Trailer>? = null
)
