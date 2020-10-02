package com.fady.moviemaster.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fady.moviemaster.R
import com.fady.moviemaster.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.getAllMoviesList().observe(this,
            Observer { movies ->
                if(!movies.isNullOrEmpty()){
                }
            })

    }
}