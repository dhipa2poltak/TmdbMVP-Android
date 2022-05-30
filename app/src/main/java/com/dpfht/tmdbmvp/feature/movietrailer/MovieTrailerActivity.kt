package com.dpfht.tmdbmvp.feature.movietrailer

import android.os.Bundle
import android.widget.Toast
import com.dpfht.tmdbmvp.PlayerConfig
import com.dpfht.tmdbmvp.R
import com.dpfht.tmdbmvp.TheApplication
import com.dpfht.tmdbmvp.databinding.ActivityMovieTrailerBinding
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerPresenter
import com.dpfht.tmdbmvp.feature.movietrailer.MovieTrailerContract.MovieTrailerView
import com.dpfht.tmdbmvp.feature.movietrailer.di.DaggerMovieTrailerComponent
import com.dpfht.tmdbmvp.feature.movietrailer.di.MovieTrailerModule
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import javax.inject.Inject

class MovieTrailerActivity : YouTubeBaseActivity(), MovieTrailerView {

  private lateinit var binding: ActivityMovieTrailerBinding

  @Inject
  lateinit var presenter: MovieTrailerPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    val movieTrailerComponent = DaggerMovieTrailerComponent
      .builder()
      .movieTrailerModule(MovieTrailerModule(this))
      .applicationComponent(TheApplication.instance.applicationComponent)
      .build()

    movieTrailerComponent.inject(this)


    super.onCreate(savedInstanceState)
    binding = ActivityMovieTrailerBinding.inflate(layoutInflater)
    setContentView(binding.root)

    if (intent.hasExtra("movie_id")) {
      val movieId = intent.getIntExtra("movie_id", -1)

      presenter.setMovieId(movieId)
      presenter.start()
    }
  }

  override fun showTrailer(keyVideo: String) {
    binding.playerYoutube.initialize(
      PlayerConfig.API_KEY,
      object : YouTubePlayer.OnInitializedListener {

        override fun onInitializationSuccess(
          p0: YouTubePlayer.Provider?,
          youtubePlayer: YouTubePlayer?,
          p2: Boolean
        ) {
          youtubePlayer?.loadVideo(keyVideo)
          youtubePlayer?.play()
        }

        override fun onInitializationFailure(
          p0: YouTubePlayer.Provider?,
          p1: YouTubeInitializationResult?
        ) {
          Toast.makeText(
            baseContext,
            "error loading youtube video",
            Toast.LENGTH_SHORT
          ).show()
        }
      })
  }

  override fun showErrorMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }

  override fun showCanceledMessage() {
    showErrorMessage(getString(R.string.canceled_message))
  }

  override fun onDestroy() {
    presenter.onDestroy()
    super.onDestroy()
  }
}
