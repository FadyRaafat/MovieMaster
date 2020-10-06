package com.fady.moviemaster.view.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fady.moviemaster.R
import com.fady.moviemaster.datamodel.models.Movie
import com.fady.moviemaster.view.adapters.MoviesAdapter
import com.fady.moviemaster.view.adapters.SearchResultAdapter
import com.fady.moviemaster.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.getAllMoviesList().observe(this,
            Observer { movies ->
                if (!movies.isNullOrEmpty()) {
                    updateMoviesRV(movies)
                }
            })

        search()

    }

    fun search(){
        search_ET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(
                searchQuery: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (searchQuery.isNotEmpty()) {
                    movieViewModel.search(searchQuery.toString()).observe(this@HomeActivity,
                        Observer { movies ->
                            if (!movies.isNullOrEmpty()) {
                                updateMoviesRV(movies)

                            }
                        })
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

    }

    fun updateMoviesRV(movies: List<Movie>) {
        loadingMovies_PB.visibility = View.GONE
        movies_RV.visibility = View.VISIBLE
        search_ET.visibility = View.VISIBLE
        val adapter = MoviesAdapter(movies)
        val layoutManager = LinearLayoutManager(this)
        movies_RV.adapter = adapter
        movies_RV.layoutManager = layoutManager
        movies_RV.addItemDecoration(
            DividerItemDecoration(
                movies_RV.context,
                DividerItemDecoration.VERTICAL
            )
        )


    }
}