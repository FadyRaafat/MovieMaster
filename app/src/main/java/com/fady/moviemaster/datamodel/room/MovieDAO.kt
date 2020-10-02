package com.fady.moviemaster.datamodel.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.fady.moviemaster.datamodel.models.Movie

@Dao
abstract class MovieDAO : BaseDao<Movie>() {
    @Query("SELECT * FROM movie")
    abstract fun getMovieLiveData(): LiveData<List<Movie>>


}