package com.fady.moviemaster.datamodel.services

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object HttpClient {
    fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

}

