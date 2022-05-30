package com.dpfht.tmdbmvp.feature.moviesbygenre

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
import com.dpfht.tmdbmvp.databinding.FragmentMoviesByGenreBinding
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenrePresenter
import com.dpfht.tmdbmvp.feature.moviesbygenre.MoviesByGenreContract.MoviesByGenreView
import com.dpfht.tmdbmvp.feature.moviesbygenre.adapter.MoviesByGenreAdapter
import com.dpfht.tmdbmvp.feature.moviesbygenre.adapter.MoviesByGenreAdapter.OnClickMovieListener
import com.dpfht.tmdbmvp.feature.moviesbygenre.di.DaggerMoviesByGenreComponent
import com.dpfht.tmdbmvp.feature.moviesbygenre.di.MoviesByGenreModule
import javax.inject.Inject

class MoviesByGenreFragment : BaseFragment(), MoviesByGenreView {

  private lateinit var binding: FragmentMoviesByGenreBinding

  @Inject
  lateinit var presenter: MoviesByGenrePresenter

  @Inject
  lateinit var adapter: MoviesByGenreAdapter

  override fun onAttach(context: Context) {
    super.onAttach(context)

    val moviesByGenreComponent = DaggerMoviesByGenreComponent
      .builder()
      .moviesByGenreModule(MoviesByGenreModule(this))
      .applicationComponent(TheApplication.instance.applicationComponent)
      .build()

    moviesByGenreComponent.inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentMoviesByGenreBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL

    binding.rvMoviesByGenre.layoutManager = layoutManager
    binding.rvMoviesByGenre.adapter = adapter

    adapter.onClickMovieListener = object : OnClickMovieListener {
      override fun onClickMovie(position: Int) {
        val navDirections = presenter.getNavDirectionsOnClickMovieAt(position)
        Navigation.findNavController(requireView()).navigate(navDirections)
      }
    }

    binding.rvMoviesByGenre.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val xx = recyclerView.computeVerticalScrollRange()
        val xy = recyclerView.computeVerticalScrollOffset()
        val xz = recyclerView.computeVerticalScrollExtent()
        val zz = (xy.toFloat() / (xx - xz).toFloat() * 100).toInt()
        if (zz >= 75 && !presenter.isLoadingData()) {
          presenter.getMoviesByGenre()
        }
        super.onScrolled(recyclerView, dx, dy)
      }
    })

    val args = MoviesByGenreFragmentArgs.fromBundle(requireArguments())
    val genreId = args.genreId
    val genreName = args.genreName

    val title = "$genreName movies"
    binding.tvTitle.text = title

    presenter.setGenreId(genreId)
    presenter.start()
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
    val navDirections = MoviesByGenreFragmentDirections.actionMovieByGenreToErrorDialog(message)
    Navigation.findNavController(requireView()).navigate(navDirections)
  }

  override fun showCanceledMessage() {
    showErrorMessage(getString(R.string.canceled_message))
  }
}
