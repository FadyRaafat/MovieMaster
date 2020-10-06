package com.fady.moviemaster.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fady.moviemaster.R
import com.fady.moviemaster.viewmodel.MovieViewModel

class SplashActivity : AppCompatActivity() {
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        movieViewModel.getFirstFiveMovies().observe(this,
            Observer { movies ->
                if (movies.isNullOrEmpty()) {
                    movieViewModel.upsertMovies(this.application)
                }
            })
    }

    private fun toHome() {
        startActivity(Intent(applicationContext, HomeActivity::class.java))
        this.finish()

    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed(
            {
                toHome()
            }, 4000
        )

    }


}