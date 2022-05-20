package com.dpfht.tmdbmvp.model.response

import com.dpfht.tmdbmvp.model.Genre

data class GenreResponse(
    var genres: List<Genre>? = null
)
