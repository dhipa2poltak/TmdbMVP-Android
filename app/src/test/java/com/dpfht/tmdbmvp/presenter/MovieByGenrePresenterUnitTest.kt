package com.dpfht.tmdbmvp.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dpfht.tmdbmvp.MainCoroutineRule
import com.dpfht.tmdbmvp.data.model.remote.Movie
import com.dpfht.tmdbmvp.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreModel
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenrePresenter
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreView
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenrePresenterImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MovieByGenrePresenterUnitTest {

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  private lateinit var presenter: MoviesByGenrePresenter

  @Mock
  private lateinit var movieByGenreView: MoviesByGenreView

  @Mock
  private lateinit var movieByGenreModel: MoviesByGenreModel

  private val listOfMovie = arrayListOf<Movie>()
  private val scope = CoroutineScope(Job())

  @Before
  fun setup() {
    presenter = MoviesByGenrePresenterImpl(movieByGenreView, movieByGenreModel, listOfMovie, scope)
  }

  @Test
  fun `fetch movie by genre successfully`() = runBlocking {
    val movie1 = Movie(id = 1, originalTitle = "title1", overview = "overview1", title = "title1")
    val movie2 = Movie(id = 2, originalTitle = "title2", overview = "overview2", title = "title2")
    val movie3 = Movie(id = 3, originalTitle = "title3", overview = "overview3", title = "title3")

    val genreId = 1
    val page = 1

    val movies = listOf(movie1, movie2, movie3)
    val getMovieByGenreResult = GetMovieByGenreResult(movies, page)
    val result = ModelResultWrapper.Success(getMovieByGenreResult)

    whenever(movieByGenreModel.getMoviesByGenre(genreId, page)).thenReturn(result)

    presenter.setGenreId(genreId)
    presenter.start()

    verify(movieByGenreView).notifyItemInserted(listOfMovie.size - 1)
    verify(movieByGenreView).hideLoadingDialog()
  }

  @Test
  fun `failed fetch movie by genre`() = runBlocking {
    val msg = "error fetch movie"
    val result = ModelResultWrapper.ErrorResult(msg)

    val genreId = 1
    val page = 1

    whenever(movieByGenreModel.getMoviesByGenre(genreId, page)).thenReturn(result)

    presenter.setGenreId(genreId)
    presenter.start()

    verify(movieByGenreView).hideLoadingDialog()
    verify(movieByGenreView).showErrorMessage(msg)
  }
}
