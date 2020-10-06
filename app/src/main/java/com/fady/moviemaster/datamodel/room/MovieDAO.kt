package com.fady.moviemaster.datamodel.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.fady.moviemaster.datamodel.models.Movie

@Dao
abstract class MovieDAO : BaseDao<Movie>() {
    @Query("SELECT * FROM movie LIMIT 250")
    abstract fun getMovieLiveData(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie LIMIT 5 ")
    abstract fun getFirstFiveMovies(): LiveData<List<Movie>>


    @Query(" SELECT * FROM (SELECT * FROM movie WHERE title GLOB '*' || :searchQuery|| '*') ORDER BY rating DESC ")
    abstract fun search(searchQuery: String): List<Movie>


}