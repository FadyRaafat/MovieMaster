package com.fady.moviemaster.datamodel.models

import com.google.gson.annotations.SerializedName

class Movies {
    @SerializedName("movies")
    var movies: List<Movie>? = null

}