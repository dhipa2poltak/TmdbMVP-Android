package com.dpfht.tmdbmvp.feature.genre

import androidx.navigation.NavDirections
import com.dpfht.tmdbmvp.base.BasePresenter
import com.dpfht.tmdbmvp.base.BaseView
import com.dpfht.tmdbmvp.domain.model.GetMovieGenreResult
import io.reactivex.Observable

interface GenreContract {

  interface GenreView: BaseView {
    fun notifyItemInserted(position: Int)
  }

  interface GenrePresenter: BasePresenter {
    fun getNavDirectionsOnClickGenreAt(position: Int): NavDirections
  }

  interface GenreModel {
    fun getMovieGenre(): Observable<GetMovieGenreResult>
  }
}
