package com.dpfht.tmdbmvp.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dpfht.tmdbmvp.RxImmediateSchedulerRule
import com.dpfht.tmdbmvp.data.model.remote.Review
import com.dpfht.tmdbmvp.domain.model.GetMovieReviewResult
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsModel
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsPresenter
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsView
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsPresenterImpl
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
class MovieReviewsPresenterUnitTest {

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val schedulers = RxImmediateSchedulerRule()

  private lateinit var presenter: MovieReviewsPresenter

  @Mock
  private lateinit var movieReviewsView: MovieReviewsView

  @Mock
  private lateinit var movieReviewsModel: MovieReviewsModel

  private val listOfReview = arrayListOf<Review>()
  private val compositeDisposable = CompositeDisposable()

  @Before
  fun setup() {
    presenter = MovieReviewsPresenterImpl(movieReviewsView, movieReviewsModel, listOfReview, compositeDisposable)
  }

  @Test
  fun `fetch review successfully`() {
    val review1 = Review(author = "author1", content = "content1", id = "1")
    val review2 = Review(author = "author2", content = "content2", id = "2")
    val review3 = Review(author = "author3", content = "content3", id = "3")

    val reviews = listOf(review1, review2, review3)

    val movieId = 1
    val page = 1

    val getMovieReviewsResult = GetMovieReviewResult(reviews, page)

    whenever(movieReviewsModel.getMovieReviews(movieId, page)).thenReturn(Observable.just(getMovieReviewsResult))

    presenter.setMovieId(movieId)
    presenter.start()

    verify(movieReviewsView).notifyItemInserted(listOfReview.size - 1)
    verify(movieReviewsView).hideLoadingDialog()
  }

  @Test
  fun `failed fetch review`() {
    val msg = "error in conversion"
    val review1 = Review(author = "author1", content = "content1", id = "1")
    val review2 = Review(author = "author2", content = "content2", id = "2")
    val review3 = Review(author = "author3", content = "content3", id = "3")

    val reviews = listOf(review1, review2, review3)

    val movieId = 1
    val page = 1

    val getMovieReviewsResult = GetMovieReviewResult(reviews, page)

    whenever(movieReviewsModel.getMovieReviews(movieId, page)).thenReturn(
      Observable.just(getMovieReviewsResult)
        .map { throw Exception(msg) }
    )

    presenter.setMovieId(movieId)
    presenter.start()

    verify(movieReviewsView).hideLoadingDialog()
    verify(movieReviewsView).showErrorMessage(msg)
  }
}
