package com.dpfht.tmdbmvp.domain.model

import com.dpfht.tmdbmvp.data.model.remote.Genre

data class GetMovieGenreResult(
  val genres: List<Genre>
)
