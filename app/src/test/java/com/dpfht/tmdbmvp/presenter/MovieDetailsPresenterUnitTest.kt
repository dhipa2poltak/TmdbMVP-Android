package com.dpfht.tmdbmvp.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dpfht.tmdbmvp.Config
import com.dpfht.tmdbmvp.RxImmediateSchedulerRule
import com.dpfht.tmdbmvp.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsModel
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsPresenter
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsContract.MovieDetailsView
import com.dpfht.tmdbmvp.feature.moviedetails.MovieDetailsPresenterImpl
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MovieDetailsPresenterUnitTest {

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val schedulers = RxImmediateSchedulerRule()

  private lateinit var presenter: MovieDetailsPresenter

  @Mock
  private lateinit var movieDetailsView: MovieDetailsView

  @Mock
  private lateinit var movieDetailsModel: MovieDetailsModel

  private val compositeDisposable = CompositeDisposable()

  @Before
  fun setup() {
    presenter = MovieDetailsPresenterImpl(movieDetailsView, movieDetailsModel, compositeDisposable)
  }

  @Test
  fun `fetch movie details successfully`() {
    val movieId = 1
    val title = "title1"
    val overview = "overview1"
    val posterPath = "poster_path1"

    val getMovieDetailsResult = GetMovieDetailsResult(movieId, title, overview, posterPath)

    whenever(movieDetailsModel.getMovieDetails(movieId)).thenReturn(Observable.just(getMovieDetailsResult))

    presenter.setMovieId(movieId)
    presenter.start()

    val imageUrl = Config.IMAGE_URL_BASE_PATH + posterPath

    verify(movieDetailsView).hideLoadingDialog()
    verify(movieDetailsView).showMovieDetails(title, overview, imageUrl)
  }

  @Test
  fun `failed fetch movie details`() {
    val msg = "error in conversion"
    val movieId = 1
    val title = "title1"
    val overview = "overview1"
    val posterPath = "poster_path1"

    val getMovieDetailsResult = GetMovieDetailsResult(movieId, title, overview, posterPath)

    whenever(movieDetailsModel.getMovieDetails(movieId)).thenReturn(
      Observable.just(getMovieDetailsResult)
        .map { throw Exception(msg) }
    )

    presenter.setMovieId(movieId)
    presenter.start()

    verify(movieDetailsView).hideLoadingDialog()
    verify(movieDetailsView).showErrorMessage(msg)
  }
}
