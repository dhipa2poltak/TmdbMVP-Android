package com.dpfht.tmdbmvp.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dpfht.tmdbmvp.RxImmediateSchedulerRule
import com.dpfht.tmdbmvp.data.model.remote.Movie
import com.dpfht.tmdbmvp.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreModel
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenrePresenter
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreView
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenrePresenterImpl
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
class MoviesByGenrePresenterUnitTest {

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val schedulers = RxImmediateSchedulerRule()

  private lateinit var presenter: MoviesByGenrePresenter

  @Mock
  private lateinit var moviesByGenreView: MoviesByGenreView

  @Mock
  private lateinit var moviesByGenreModel: MoviesByGenreModel

  private val listOfMovie = arrayListOf<Movie>()
  private val compositeDisposable = CompositeDisposable()

  @Before
  fun setup() {
    presenter = MoviesByGenrePresenterImpl(moviesByGenreView, moviesByGenreModel, listOfMovie, compositeDisposable)
  }

  @Test
  fun `fetch movie by genre successfully`() {
    val movie1 = Movie(id = 1, originalTitle = "title1", overview = "overview1", title = "title1")
    val movie2 = Movie(id = 2, originalTitle = "title2", overview = "overview2", title = "title2")
    val movie3 = Movie(id = 3, originalTitle = "title3", overview = "overview3", title = "title3")

    val genreId = 1
    val page = 1

    val movies = listOf(movie1, movie2, movie3)
    val getMoviesByGenreResult = GetMovieByGenreResult(movies, page)

    whenever(moviesByGenreModel.getMoviesByGenre(genreId, page)).thenReturn(Observable.just(getMoviesByGenreResult))

    presenter.setGenreId(genreId)
    presenter.start()

    verify(moviesByGenreView).notifyItemInserted(listOfMovie.size - 1)
    verify(moviesByGenreView).hideLoadingDialog()
  }

  @Test
  fun `failed fetch movie by genre`() {
    val msg = "error in conversion"
    val movie1 = Movie(id = 1, originalTitle = "title1", overview = "overview1", title = "title1")
    val movie2 = Movie(id = 2, originalTitle = "title2", overview = "overview2", title = "title2")
    val movie3 = Movie(id = 3, originalTitle = "title3", overview = "overview3", title = "title3")

    val genreId = 1
    val page = 1

    val movies = listOf(movie1, movie2, movie3)
    val getMoviesByGenreResult = GetMovieByGenreResult(movies, page)

    whenever(moviesByGenreModel.getMoviesByGenre(genreId, page)).thenReturn(
      Observable.just(getMoviesByGenreResult)
        .map { throw Exception(msg) }
    )

    presenter.setGenreId(genreId)
    presenter.start()

    verify(moviesByGenreView).hideLoadingDialog()
    verify(moviesByGenreView).showErrorMessage(msg)
  }
}
