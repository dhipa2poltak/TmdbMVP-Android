package com.dpfht.tmdbmvp.feature.moviereviews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.tmdbmvp.R
import com.dpfht.tmdbmvp.TheApplication
import com.dpfht.tmdbmvp.base.BaseFragment
import com.dpfht.tmdbmvp.databinding.FragmentMovieReviewsBinding
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsPresenter
import com.dpfht.tmdbmvp.feature.moviereviews.MovieReviewsContract.MovieReviewsView
import com.dpfht.tmdbmvp.feature.moviereviews.adapter.MovieReviewsAdapter
import com.dpfht.tmdbmvp.feature.moviereviews.di.DaggerMovieReviewsComponent
import com.dpfht.tmdbmvp.feature.moviereviews.di.MovieReviewsModule
import javax.inject.Inject

class MovieReviewsFragment : BaseFragment(), MovieReviewsView {

  private lateinit var binding: FragmentMovieReviewsBinding

  @Inject
  lateinit var presenter: MovieReviewsPresenter

  @Inject
  lateinit var adapter: MovieReviewsAdapter

  override fun onAttach(context: Context) {
    super.onAttach(context)

    val movieReviewsComponent = DaggerMovieReviewsComponent
      .builder()
      .movieReviewsModule(MovieReviewsModule(this))
      .applicationComponent(TheApplication.instance.applicationComponent)
      .build()

    movieReviewsComponent.inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentMovieReviewsBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL

    binding.rvReview.layoutManager = layoutManager
    binding.rvReview.adapter = adapter

    binding.rvReview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val xx = recyclerView.computeVerticalScrollRange()
        val xy = recyclerView.computeVerticalScrollOffset()
        val xz = recyclerView.computeVerticalScrollExtent()
        val zz = (xy.toFloat() / (xx - xz).toFloat() * 100).toInt()
        if (zz >= 75 && !presenter.isLoadingData()) {
          presenter.getMovieReviews()
        }
        super.onScrolled(recyclerView, dx, dy)
      }
    })

    val args = MovieReviewsFragmentArgs.fromBundle(requireArguments())
    val movieId = args.movieId
    val movieTitle = args.movieTitle

    binding.tvMovieName.text = movieTitle

    if (movieId != -1 && presenter.isEmptyReviews()) {
      presenter.setMovieIdValue(movieId)
      presenter.getMovieReviews()
    }
  }

  override fun notifyItemInserted(position: Int) {
    adapter.notifyItemInserted(position)
  }

  override fun onDetach() {
    presenter.onDestroy()
    super.onDetach()
  }

  override fun showLoadingDialog() {
    prgDialog.show()
  }

  override fun hideLoadingDialog() {
    prgDialog.dismiss()
  }

  override fun showErrorMessage(message: String) {
    val navDirections = MovieReviewsFragmentDirections.actionMovieReviewsToErrorDialog(message)
    Navigation.findNavController(requireView()).navigate(navDirections)
  }

  override fun showCanceledMessage() {
    showErrorMessage(getString(R.string.canceled_message))
  }
}
