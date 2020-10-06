package com.fady.moviemaster.datamodel.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.fady.moviemaster.datamodel.models.Movie
import com.fady.moviemaster.datamodel.models.Movies
import com.fady.moviemaster.datamodel.room.AppDataBase
import com.fady.moviemaster.datamodel.room.MovieDAO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MovieRepository(application: Application) {
    private val appDB: AppDataBase = AppDataBase.getDatabase(application)!!
    private val movieDAO: MovieDAO
    val moviesMutableLiveData = MutableLiveData<List<Movie>>()


    init {
        movieDAO = appDB.getMovie()
    }

    fun getAllMoviesList(): LiveData<List<Movie>> {
        return movieDAO.getMovieLiveData()
    }

    fun getFirstFiveMovies(): LiveData<List<Movie>> {
        return movieDAO.getFirstFiveMovies()
    }


    private fun bufferJSONFile(applicationContext: Application): String {
        return applicationContext.assets.open("movies.json").bufferedReader().use { it.readText() }
    }

    private fun parseJSONToModel(applicationContext: Application): Movies {
        val gson = Gson()
        val listMoviesType = object : TypeToken<Movies>() {}.type
        return gson.fromJson(bufferJSONFile(applicationContext), listMoviesType)
    }

    fun upsertingMoviesList(applicationContext: Application) {
        val movies = parseJSONToModel(applicationContext).movies!!
        movieDAO.upsert(movies)
    }

    fun search(searchQuery: String) {
        val movies = movieDAO.search(searchQuery)
        val mappedList = arrayListOf<Movie>()
        var yearStackSize = 0
        var oldYear = movies[0].year
        for (i in movies.indices) {
            if (oldYear == movies[i].year) {
                if (yearStackSize < 4) {
                    oldYear = movies[i].year!!
                    yearStackSize++
                    mappedList.add(movies[i])

                } else {
                    continue
                }
            } else {
                oldYear = movies[i].year!!
                yearStackSize = 0
                mappedList.add(movies[i])
            }
        }
        moviesMutableLiveData.postValue(mappedList)

    }


}