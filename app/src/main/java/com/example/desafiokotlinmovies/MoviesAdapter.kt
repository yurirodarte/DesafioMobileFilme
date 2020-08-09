package com.example.desafiokotlinmovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import org.w3c.dom.Text

class MoviesAdapter(private var movies: MutableList<Filmes>,
                    private val onMovieClick: (movie: Filmes) -> Unit) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    fun appendMovies(movies: List<Filmes>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(
            this.movies.size,
            movies.size - 1
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun updateMovies(movies: List<Filmes>) {
        this.movies = movies as MutableList<Filmes>
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)
        private val title: TextView = itemView.findViewById(R.id.nome_filme)
        private val date: TextView = itemView.findViewById(R.id.ano_filme)
        private val ratingBar: TextView = itemView.findViewById(R.id.view)
        private val voteCount: TextView = itemView.findViewById(R.id.vote_count)

        fun bind(movie: Filmes) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .transform(CenterCrop())
                .into(poster)
            itemView.setOnClickListener { onMovieClick.invoke(movie) }
            title.text = movie.title
            date.text = movie.releaseDate
            ratingBar.text = movie.rating.toString()
            voteCount.text = movie.votecount.toString()
        }
    }
}