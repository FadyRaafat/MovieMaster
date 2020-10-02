package com.fady.moviemaster.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.fady.moviemaster.datamodel.models.Movies
import com.fady.moviemaster.viewmodel.MovieViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        upsertingMoviesList()
        startActivity(Intent(applicationContext, HomeActivity::class.java))
    }

    private fun bufferJSONFile(): String {
        return applicationContext.assets.open("movies.json").bufferedReader().use { it.readText() }
    }

    private fun parseJSONToModel(): Movies {
        val gson = Gson()
        val listMoviesType = object : TypeToken<Movies>() {}.type
        return gson.fromJson(bufferJSONFile(), listMoviesType)
    }

    private fun upsertingMoviesList(){
        movieViewModel.upsertMovies(parseJSONToModel().movies!!)
    }
}