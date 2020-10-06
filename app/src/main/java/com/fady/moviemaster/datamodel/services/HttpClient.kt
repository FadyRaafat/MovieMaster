package com.fady.moviemaster.datamodel.services

import com.fady.moviemaster.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object HttpClient {
    fun getClient(): OkHttpClient {

        var logger: HttpLoggingInterceptor? = null
        if (BuildConfig.DEBUG) {
            logger = HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
        }

        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(logger)
            .build()
    }

}

