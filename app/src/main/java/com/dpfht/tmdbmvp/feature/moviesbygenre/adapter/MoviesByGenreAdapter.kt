package com.dpfht.tmdbmvp.feature.moviesbygenre.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.tmdbmvp.databinding.RowMovieBinding
import com.dpfht.tmdbmvp.model.Movie

class MoviesByGenreAdapter(private val movies: ArrayList<Movie>): RecyclerView.Adapter<MoviesByGenreAdapter.MovieByGenreHolder>() {

    var onClickMovieListener: OnClickMovieListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieByGenreHolder {
        val binding = RowMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieByGenreHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieByGenreHolder, position: Int) {
        holder.bindData(movies[position])
        holder.itemView.setOnClickListener {
            onClickMovieListener?.onClickMovie(position)
        }
    }

    class MovieByGenreHolder(private val binding: RowMovieBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindData(movie: Movie) {
            binding.tvTitleMovie.text = movie.title
        }
    }

    interface OnClickMovieListener {
        fun onClickMovie(position: Int)
    }
}
