package com.fady.moviemaster.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fady.moviemaster.datamodel.models.Movie
import com.fady.moviemaster.datamodel.repository.MovieRepository

class MovieViewModel(application: Application) :
    AndroidViewModel(application) {
    private val mRepository: MovieRepository = MovieRepository(application)


    fun upsertMovies(applicationContext: Application) {
        mRepository.upsertingMoviesList(applicationContext)
    }

    fun getAllMoviesList(): LiveData<List<Movie>> {
        return mRepository.getAllMoviesList()
    }

    fun getFirstFiveMovies(): LiveData<List<Movie>> {
        return mRepository.getFirstFiveMovies()
    }


    fun search(searchQuery: String): MutableLiveData<List<Movie>> {
        mRepository.search(searchQuery)
        return mRepository.moviesMutableLiveData

    }


}