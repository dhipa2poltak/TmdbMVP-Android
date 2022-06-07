package com.dpfht.tmdbmvp.domain.model

import com.dpfht.tmdbmvp.data.model.remote.Trailer

data class GetMovieTrailerResult(
  val trailers: List<Trailer>
)
