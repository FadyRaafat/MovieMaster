package com.fady.moviemaster.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fady.moviemaster.datamodel.models.FlickrResponse
import com.fady.moviemaster.datamodel.repository.MovieDetailsRepository
import com.fady.moviemaster.datamodel.repository.MovieRepository

class MovieDetailsViewModel(application: Application) :
    AndroidViewModel(application) {
    private val mRepository: MovieDetailsRepository = MovieDetailsRepository(application)

    fun requestMovieStatus(movieTitle: String): MutableLiveData<String> {
        mRepository.getUserDataRemote(movieTitle)
        return mRepository.requestStatusMutableLiveData
    }

    fun getMoviePhotos(): MutableLiveData<FlickrResponse> {
        return mRepository.photosMutableLiveData

    }


}