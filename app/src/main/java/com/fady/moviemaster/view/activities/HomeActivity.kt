package com.fady.moviemaster.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fady.moviemaster.R
import com.fady.moviemaster.datamodel.models.Movie
import com.fady.moviemaster.view.adapters.MoviesAdapter
import com.fady.moviemaster.view.adapters.searchresult.MoviesSection
import com.fady.moviemaster.viewmodel.MovieViewModel
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(), MoviesSection.ClickListener {
    private lateinit var movieViewModel: MovieViewModel
    var sectionedAdapter: SectionedRecyclerViewAdapter? = null

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

        initView()

    }

    private fun initView() {
        search_BTN.setOnClickListener {
            if (!search_ET.text.toString().trim().isBlank()) {
                search_BTN.isClickable = false
                search_BTN.alpha = 0.5f
                loadingView()
                movieViewModel.search(search_ET.text.toString().trim()).observe(this@HomeActivity,
                    Observer { movies ->
                        if (!movies.isNullOrEmpty()) {
                            fillingView()
                            updateSearchResultRV(movies)
                        }
                    })
            } else {
                Toast.makeText(this, "Please Enter Search Value", Toast.LENGTH_SHORT).show()
            }

        }
    }


    private fun loadingView() {
        loadingMovies_PB.visibility = View.VISIBLE
        title_TV.visibility = View.VISIBLE
        search_ET.visibility = View.VISIBLE
        search_BTN.visibility = View.VISIBLE
        movies_RV.visibility = View.GONE

    }

    private fun fillingView() {
        loadingMovies_PB.visibility = View.INVISIBLE
        title_TV.visibility = View.VISIBLE
        movies_RV.visibility = View.VISIBLE
        search_ET.visibility = View.VISIBLE
        search_BTN.visibility = View.VISIBLE
        search_BTN.isClickable = true
        search_BTN.alpha = 1f


    }

    private fun updateSearchResultRV(list: MutableMap<Int, List<Movie>>) {
        sectionedAdapter = SectionedRecyclerViewAdapter()
        for ((key, value) in list) {
            if (value.isNotEmpty()) {
                sectionedAdapter!!.addSection(MoviesSection(key.toString(), value, this))
            }
        }
        movies_RV.adapter = sectionedAdapter
        initRecyclerView()
    }

    private fun updateMoviesRV(movies: List<Movie>) {
        fillingView()
        val adapter = MoviesAdapter(movies)
        movies_RV.adapter = adapter
        initRecyclerView()
    }

    private fun initRecyclerView() {
        movies_RV.isNestedScrollingEnabled = false
        movies_RV.layoutManager = LinearLayoutManager(this)

    }

    override fun onItemRootViewClicked(
        selectedMovie: Movie
    ) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("selectedMovie", selectedMovie)
        startActivity(intent)

    }


}