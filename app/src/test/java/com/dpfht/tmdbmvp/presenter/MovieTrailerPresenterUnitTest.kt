package com.dpfht.tmdbmvp.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dpfht.tmdbmvp.MainCoroutineRule
import com.dpfht.tmdbmvp.data.model.remote.Trailer
import com.dpfht.tmdbmvp.domain.model.GetMovieTrailerResult
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerModel
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerPresenter
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerView
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerPresenterImpl
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
class MovieTrailerPresenterUnitTest {

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  private lateinit var presenter: MovieTrailerPresenter

  @Mock
  private lateinit var movieTrailerView: MovieTrailerView

  @Mock
  private lateinit var movieTrailerModel: MovieTrailerModel

  private val scope = CoroutineScope(Job())

  @Before
  fun setup() {
    presenter = MovieTrailerPresenterImpl(movieTrailerView, movieTrailerModel, scope)
  }

  @Test
  fun `fetch movie trailer successfully`() = runBlocking {
    val keyVideo1 = "11111"
    val trailer1 = Trailer(id = "1", key = keyVideo1, name = "name1", site = "youtube")
    val trailer2 = Trailer(id = "2", key = "22222", name = "name2", site = "youtube")
    val trailer3 = Trailer(id = "3", key = "33333", name = "name3", site = "youtube")

    val trailers = listOf(trailer1, trailer2, trailer3)
    val getMovieTrailerResult = GetMovieTrailerResult(trailers)
    val result = ModelResultWrapper.Success(getMovieTrailerResult)

    val movieId = 1

    whenever(movieTrailerModel.getMovieTrailer(movieId)).thenReturn(result)

    presenter.setMovieId(movieId)
    presenter.start()

    verify(movieTrailerView).showTrailer(keyVideo1)
  }

  @Test
  fun `failed fetch movie trailer`() = runBlocking {
    val msg = "error fetch movie trailer"
    val result = ModelResultWrapper.ErrorResult(msg)

    val movieId = 1

    whenever(movieTrailerModel.getMovieTrailer(movieId)).thenReturn(result)

    presenter.setMovieId(movieId)
    presenter.start()

    verify(movieTrailerView).showErrorMessage(msg)
  }
}