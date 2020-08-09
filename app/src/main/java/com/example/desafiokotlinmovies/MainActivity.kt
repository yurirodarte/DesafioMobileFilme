package com.example.desafiokotlinmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_movie.*
import kotlinx.android.synthetic.main.item_movie.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var popularFilmes: RecyclerView
    private lateinit var popularFilmesAdapter: MoviesAdapter
    private lateinit var popularFilmesLayoutMgr: LinearLayoutManager

    private var popularFilmesPage = 1

    private fun showMovieDetails(movie: Filmes) {
        val intent = Intent(this, FilmesDetalhesActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        popularFilmes = findViewById(R.id.popular_movies)
        popularFilmesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        popularFilmes.layoutManager = popularFilmesLayoutMgr
        popularFilmesAdapter = MoviesAdapter(mutableListOf()){ movie -> showMovieDetails(movie) }
        popularFilmes.adapter = popularFilmesAdapter

        getPopularFilmes()
    }

    private fun getPopularFilmes() {
        FilmesRepository.getPopularFilmes(
            popularFilmesPage,
            ::onPopularMoviesFetched,
            ::onError
        )
    }

    private fun onPopularMoviesFetched(movies: List<Filmes>) {
        popularFilmesAdapter.appendMovies(movies)
        attachPopularMoviesOnScrollListener()
    }

    private fun attachPopularMoviesOnScrollListener() {
        popularFilmes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularFilmesLayoutMgr.itemCount
                val visibleItemCount = popularFilmesLayoutMgr.childCount
                val firstVisibleItem = popularFilmesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popularFilmes.removeOnScrollListener(this)
                    popularFilmesPage++
                    getPopularFilmes()
                }
            }
        })
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }
}
