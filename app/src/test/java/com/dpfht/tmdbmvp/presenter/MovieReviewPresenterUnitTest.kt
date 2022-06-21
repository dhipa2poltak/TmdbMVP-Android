package com.dpfht.tmdbmvp.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dpfht.tmdbmvp.MainCoroutineRule
import com.dpfht.tmdbmvp.data.model.remote.Review
import com.dpfht.tmdbmvp.domain.model.GetMovieReviewResult
import com.dpfht.tmdbmvp.domain.model.ModelResultWrapper
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsModel
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsPresenter
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsView
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsPresenterImpl
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
class MovieReviewPresenterUnitTest {

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  private lateinit var presenter: MovieReviewsPresenter

  @Mock
  private lateinit var movieReviewsView: MovieReviewsView

  @Mock
  private lateinit var movieReviewsModel: MovieReviewsModel

  private val listOfReview = arrayListOf<Review>()
  private val scope = CoroutineScope(Job())

  @Before
  fun setup() {
    presenter = MovieReviewsPresenterImpl(movieReviewsView, movieReviewsModel, listOfReview, scope)
  }

  @Test
  fun `fetch movie review successfully`() = runBlocking {
    val review1 = Review(author = "author1", content = "content1", id = "1", url = "url1")
    val review2 = Review(author = "author2", content = "content2", id = "2", url = "url2")
    val reviews = listOf(review1, review2)

    val movieId = 1
    val page = 1

    val getMovieReviewsResult = GetMovieReviewResult(reviews, page)
    val result = ModelResultWrapper.Success(getMovieReviewsResult)

    whenever(movieReviewsModel.getMovieReviews(movieId, page)).thenReturn(result)

    presenter.setMovieId(movieId)
    presenter.start()

    verify(movieReviewsView).notifyItemInserted(listOfReview.size - 1)
    verify(movieReviewsView).hideLoadingDialog()
  }

  @Test
  fun `failed fetch movie review`() = runBlocking {
    val msg = "error fetch movie review"
    val result = ModelResultWrapper.ErrorResult(msg)

    val movieId = 1
    val page = 1

    whenever(movieReviewsModel.getMovieReviews(movieId, page)).thenReturn(result)

    presenter.setMovieId(movieId)
    presenter.start()

    verify(movieReviewsView).hideLoadingDialog()
    verify(movieReviewsView).showErrorMessage(msg)
  }
}
