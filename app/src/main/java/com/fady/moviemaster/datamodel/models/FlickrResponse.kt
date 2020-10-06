package com.fady.moviemaster.datamodel.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FlickrResponse {
    @SerializedName("photos")
    var photos: Photos? = null

    @SerializedName("stat")
    var stat: String? = null

}