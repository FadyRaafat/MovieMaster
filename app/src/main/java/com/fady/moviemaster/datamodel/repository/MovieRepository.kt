package com.fady.moviemaster.datamodel.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.fady.moviemaster.datamodel.models.Movie
import com.fady.moviemaster.datamodel.room.AppDataBase
import com.fady.moviemaster.datamodel.room.MovieDAO

class MovieRepository(application: Application) {
    private val appDB: AppDataBase = AppDataBase.getDatabase(application)!!
    private val movieDAO: MovieDAO


    init {
        movieDAO = appDB.getMovie()
    }

    fun upsert(movie: List<Movie>)
    {
        movieDAO.upsert(movie)
    }

    fun getAllMoviesList(): LiveData<List<Movie>> {
         return  movieDAO.getMovieLiveData()
    }



}