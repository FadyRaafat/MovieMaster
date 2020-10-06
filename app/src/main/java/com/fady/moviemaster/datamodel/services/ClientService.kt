package com.fady.moviemaster.datamodel.services

import com.fady.moviemaster.datamodel.models.FlickrResponse
import com.fady.moviemaster.utilis.Constants
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ClientService {

    companion object {
        private const val baseUrl = "http://api.flickr.com/services/rest/"

        fun getClient(): ClientService {

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(HttpClient.getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

            return retrofit.create(ClientService::class.java)
        }

    }

    @GET("?")
    fun getMoviePhotos(
        @Query("method") method : String,
        @Query("api_key") apiKey : String,
        @Query("text") movieTitle : String,
        @Query("page") page : Int,
        @Query("per_page") perPage : Int,
        @Query("format") format : String,
        @Query("nojsoncallback") nojsoncallback : String
        ) : Observable<FlickrResponse>

}