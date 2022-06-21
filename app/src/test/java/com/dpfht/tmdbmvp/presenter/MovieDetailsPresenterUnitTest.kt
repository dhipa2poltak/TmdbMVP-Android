package com.dpfht.tmdbmvp.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dpfht.tmdbmvp.Config
import com.dpfht.tmdbmvp.MainCoroutineRule
import com.dpfht.tmdbmvp.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsModel
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsPresenter
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsView
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsPresenterImpl
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
class MovieDetailsPresenterUnitTest {

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  private lateinit var presenter: MovieDetailsPresenter

  @Mock
  private lateinit var movieDetailsView: MovieDetailsView

  @Mock
  private lateinit var moviewDetailsModel: MovieDetailsModel

  private val scope = CoroutineScope(Job())

  @Before
  fun setup() {
    presenter = MovieDetailsPresenterImpl(movieDetailsView, moviewDetailsModel, scope)
  }

  @Test
  fun `fetch movie details successfully`() = runBlocking {
    val movieId = 1
    val title = "title1"
    val overview = "overview1"
    val posterPath = "poster_path1"

    val getMovieDetailsResult = GetMovieDetailsResult(
      movieId, title, overview, posterPath
    )

    val result = ModelResultWrapper.Success(getMovieDetailsResult)

    whenever(moviewDetailsModel.getMovieDetails(movieId)).thenReturn(result)

    presenter.setMovieId(movieId)
    presenter.start()

    var imageUrl = ""
    if (posterPath.isNotEmpty()) {
      imageUrl = Config.IMAGE_URL_BASE_PATH + posterPath
    }

    verify(movieDetailsView).showMovieDetails(title, overview, imageUrl)
    verify(movieDetailsView).hideLoadingDialog()
  }

  @Test
  fun `failed fetch movie details`() = runBlocking {
    val msg = "error fetch movie details"
    val result = ModelResultWrapper.ErrorResult(msg)

    val movieId = 1

    whenever(moviewDetailsModel.getMovieDetails(movieId)).thenReturn(result)

    presenter.setMovieId(movieId)
    presenter.start()

    verify(movieDetailsView).hideLoadingDialog()
    verify(movieDetailsView).showErrorMessage(msg)
  }
}
