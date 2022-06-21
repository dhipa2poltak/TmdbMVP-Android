package com.dpfht.tmdbmvp.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dpfht.tmdbmvp.RxImmediateSchedulerRule
import com.dpfht.tmdbmvp.data.model.remote.Genre
import com.dpfht.tmdbmvp.domain.model.GetMovieGenreResult
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreModel
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenrePresenter
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreView
import com.dpfht.tmdbmvp.feature.genre.GenrePresenterImpl
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GenrePresenterUnitTest {

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val schedulers = RxImmediateSchedulerRule()

  private lateinit var presenter: GenrePresenter

  @Mock
  private lateinit var genreView: GenreView

  @Mock
  private lateinit var genreModel: GenreModel

  private val listOfGenre = arrayListOf<Genre>()
  private val compositeDisposable = CompositeDisposable()

  @Before
  fun setup() {
    presenter = GenrePresenterImpl(genreView, genreModel, listOfGenre, compositeDisposable)
  }

  @Test
  fun `fetch movie genre successfully`() {
    val genre1 = Genre(1, "Cartoon")
    val genre2 = Genre(2, "Drama")
    val genre3 = Genre(3, "Horror")

    val genres = listOf(genre1, genre2, genre3)
    val getMovieGenreResult = GetMovieGenreResult(genres)

    whenever(genreModel.getMovieGenre()).thenReturn(Observable.just(getMovieGenreResult))

    presenter.start()

    verify(genreView).notifyItemInserted(listOfGenre.size - 1)
    verify(genreView).hideLoadingDialog()
  }

  @Test
  fun `failed fetch movie genre`() {
    val msg = "error in conversion"
    val genre1 = Genre(1, "Cartoon")
    val genre2 = Genre(2, "Drama")
    val genre3 = Genre(3, "Horror")

    val genres = listOf(genre1, genre2, genre3)
    val getMovieGenreResult = GetMovieGenreResult(genres)

    whenever(genreModel.getMovieGenre()).thenReturn(
      Observable.just(getMovieGenreResult)
        .map { throw Exception(msg) }
    )

    presenter.start()

    verify(genreView).hideLoadingDialog()
    verify(genreView).showErrorMessage(msg)
  }
}
