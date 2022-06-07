package com.dpfht.tmdbmvp.domain.model

import com.dpfht.tmdbmvp.data.model.remote.Movie

data class GetMovieByGenreResult(
  val movies: List<Movie>,
  val page: Int
)
