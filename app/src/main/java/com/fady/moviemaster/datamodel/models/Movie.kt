package com.fady.moviemaster.datamodel.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey
    var title: String,

    var year: Int?,

    var cast: List<String>?,

    var genres: List<String>?,

    var rating: Int?
)