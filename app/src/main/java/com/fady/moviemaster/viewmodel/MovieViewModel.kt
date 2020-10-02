package com.fady.moviemaster.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fady.moviemaster.datamodel.models.Movie
import com.fady.moviemaster.datamodel.repository.MovieRepository

class MovieViewModel(application: Application) :
    AndroidViewModel(application) {
    private val mRepository: MovieRepository = MovieRepository(application)

    fun upsertMovies(movies: List<Movie>) {
        mRepository.upsert(movies)
    }
    fun getAllMoviesList(): LiveData<List<Movie>> {
        return  mRepository.getAllMoviesList()
    }


}