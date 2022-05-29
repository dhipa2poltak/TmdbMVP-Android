package com.dpfht.tmdbmvp.feature.genre

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.tmdbmvp.R
import com.dpfht.tmdbmvp.TheApplication
import com.dpfht.tmdbmvp.base.BaseFragment
import com.dpfht.tmdbmvp.databinding.FragmentGenreBinding
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenrePresenter
import com.dpfht.tmdbmvp.feature.genre.GenreContract.GenreView
import com.dpfht.tmdbmvp.feature.genre.adapter.GenreAdapter
import com.dpfht.tmdbmvp.feature.genre.di.DaggerGenreComponent
import com.dpfht.tmdbmvp.feature.genre.di.GenreModule
import javax.inject.Inject

class GenreFragment : BaseFragment(), GenreView {

  private lateinit var binding: FragmentGenreBinding

  @Inject
  lateinit var presenter: GenrePresenter
  
  @Inject
  lateinit var adapter: GenreAdapter

  override fun onAttach(context: Context) {
    super.onAttach(context)

    val genreComponent = DaggerGenreComponent
      .builder()
      .genreModule(GenreModule(this))
      .applicationComponent(TheApplication.instance.applicationComponent)
      .build()

    genreComponent.inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentGenreBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL

    binding.rvGenre.layoutManager = layoutManager
    binding.rvGenre.adapter = adapter

    adapter.onClickGenreListener = object : GenreAdapter.OnClickGenreListener {
      override fun onClickGenre(position: Int) {
        val navDirections = presenter.getNavDirectionsOnClickGenreAt(position)
        Navigation.findNavController(requireView()).navigate(navDirections)
      }
    }

    if (presenter.isEmptyGenres()) {
      presenter.getMovieGenre()
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
    val navDirections = GenreFragmentDirections.actionGenreFragmentToErrorDialog(message)
    Navigation.findNavController(requireView()).navigate(navDirections)
  }

  override fun showCanceledMessage() {
    showErrorMessage(getString(R.string.canceled_message))
  }
}
