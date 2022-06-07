package com.dpfht.tmdbmvp.domain.model

import com.dpfht.tmdbmvp.data.model.remote.Review

data class GetMovieReviewResult(
  val reviews: List<Review>,
  val page: Int
)
