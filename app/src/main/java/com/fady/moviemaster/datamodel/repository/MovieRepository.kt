package com.fady.moviemaster.datamodel.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fady.moviemaster.datamodel.models.Movie
import com.fady.moviemaster.datamodel.models.Movies
import com.fady.moviemaster.datamodel.room.AppDataBase
import com.fady.moviemaster.datamodel.room.MovieDAO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import java.util.concurrent.Executors
import java.util.stream.Collectors
import kotlin.collections.ArrayList

class MovieRepository(application: Application) {
    private val appDB: AppDataBase = AppDataBase.getDatabase(application)!!
    private val movieDAO: MovieDAO
    val moviesMutableLiveData = MutableLiveData<MutableMap<Int, List<Movie>>>()


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
        Executors.newSingleThreadExecutor().execute {
            val movies = parseJSONToModel(applicationContext).movies!!
            movieDAO.upsert(movies)
        }
    }


    fun executeSearch(searchQuery: String) {
        val queriedMoviesResult = search(searchQuery)
        val yearsList = extractYearList(queriedMoviesResult)
        moviesMutableLiveData.postValue(execute(yearsList, queriedMoviesResult))

    }

    fun search(searchQuery: String): ArrayList<Movie> {
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
        return mappedList

    }

    private fun extractYearList(searchResults: ArrayList<Movie>): List<Int> {
        val yearsList: MutableList<Int> = ArrayList()
        for (i in searchResults.indices) {
            yearsList.add(searchResults[i].year!!)
        }
        return yearsList.distinct()


    }


    private fun execute(
        yearsList: List<Int>,
        moviesSearchResult: List<Movie>
    ): MutableMap<Int, List<Movie>> {
        val map: MutableMap<Int, List<Movie>> =
            LinkedHashMap()
        for (i in yearsList.indices) {
            val filteredMovies: List<Movie> = getMoviesWithYear(moviesSearchResult, yearsList[i])
            if (filteredMovies.isNotEmpty()) {
                map[yearsList[i]] = filteredMovies
            }

        }
        return map
    }

    private fun getMoviesWithYear(
        moviesSearchResult: List<Movie>,
        year: Int
    ): List<Movie> {
        val moviesList: MutableList<Movie> = ArrayList()
        for (i in moviesSearchResult.indices) {
            if (year == moviesSearchResult[i].year) {
                moviesList.add(moviesSearchResult[i])
            }
        }

        return moviesList.take(5)
    }


}