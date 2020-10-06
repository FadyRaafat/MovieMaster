package com.fady.moviemaster.datamodel.repository

import androidx.lifecycle.MutableLiveData
import com.fady.moviemaster.datamodel.models.FlickrResponse
import com.fady.moviemaster.datamodel.services.ClientService
import com.fady.moviemaster.utilis.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsRepository {

    private var clientService = ClientService.getClient()
    val requestStatusMutableLiveData = MutableLiveData<String>()
    val photosMutableLiveData = MutableLiveData<FlickrResponse>()
    var compositeDisposable = CompositeDisposable()


    private fun getMoviePhotos(movieTitle: String): Observable<FlickrResponse> {
        return clientService.getMoviePhotos(Constants.METHOD_PARAMETER,
        Constants.API_KEY, movieTitle,1,10,Constants.FORMAT,Constants.NOTJSONCALLABACK)

    }

    fun getUserDataRemote(movieTitle: String) {
        val userDataDisposable =
            getMoviePhotos(movieTitle)
                .subscribeOn(Schedulers.io())
                .retry(Constants.NUMBER_OF_RETRIES)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { photos ->
                        requestStatusMutableLiveData.value = Constants.STATUS_LOADED
                        photosMutableLiveData.value = photos
                    },
                    {
                        requestStatusMutableLiveData.value = Constants.STATUS_ERROR
                    })
        compositeDisposable.add(userDataDisposable!!)
    }


}