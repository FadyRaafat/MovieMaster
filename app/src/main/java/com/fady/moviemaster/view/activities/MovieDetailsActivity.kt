package com.fady.moviemaster.view.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.fady.moviemaster.R
import com.fady.moviemaster.datamodel.models.Movie
import com.fady.moviemaster.datamodel.models.Photo
import com.fady.moviemaster.utilis.Constants
import com.fady.moviemaster.view.adapters.PhotosAdapter
import com.fady.moviemaster.viewmodel.MovieDetailsViewModel
import kotlinx.android.synthetic.main.activity_movie_details.*

@SuppressLint("SetTextI18n")
class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    var selectedMovie: Movie? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        movieDetailsViewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)

        val intent = this.intent
        val bundle = intent.extras

        selectedMovie = bundle!!.getSerializable("selectedMovie") as Movie
        initView(selectedMovie!!)
        getMoviePhotos(selectedMovie!!)
    }

    private fun initView(movie: Movie) {
        movieTitle_TV.text = movie.title
        movieYear_TV.text = movie.year.toString()
        val builder = StringBuilder()
        for (details in movie.genres!!) {
            builder.append("$details, ")
        }

        val bul = StringBuilder()
        for (details in movie.cast!!) {
            bul.append("$details, ")
        }
        movieGenre_TV.text = builder.toString()
        movieStaff_TV.text = bul.toString()

    }

    private fun getMoviePhotos(movie: Movie) {
        movieDetailsViewModel.requestMovieStatus(movie.title).observe(this,
            Observer { status ->
                if (status == Constants.STATUS_LOADED) {
                    movieDetailsViewModel.getMoviePhotos().observe(this,
                        Observer { response ->
                            if (!response.photos!!.photo.isNullOrEmpty()) {
                                successView()
                                initPhotosRV(response.photos!!.photo!!)
                            } else {
                                noPhotosView()
                            }
                        })
                } else {
                    errorView()

                }
            })
    }

    private fun initPhotosRV(photos: List<Photo>) {
        val adapter = PhotosAdapter(photos)
        photos_RV.adapter = adapter
        photos_RV.isNestedScrollingEnabled = false
        photos_RV.layoutManager = GridLayoutManager(this, 3)
        photos_RV.isNestedScrollingEnabled = false

    }

    private fun errorView() {
        photos_RV.visibility = View.INVISIBLE
        photos_PR.visibility = View.INVISIBLE
        requestStatus_TV.visibility = View.VISIBLE
        retry_BTN.visibility = View.VISIBLE
        requestStatus_TV.text = "Something went wrong please try again"
        retry_BTN.setOnClickListener {
            getMoviePhotos(selectedMovie!!)
        }
    }

    private fun successView() {
        photos_RV.visibility = View.VISIBLE
        photos_PR.visibility = View.INVISIBLE
        requestStatus_TV.visibility = View.INVISIBLE
        retry_BTN.visibility = View.INVISIBLE

    }

    private fun noPhotosView() {
        photos_RV.visibility = View.INVISIBLE
        photos_PR.visibility = View.INVISIBLE
        requestStatus_TV.visibility = View.VISIBLE
        retry_BTN.visibility = View.INVISIBLE
        requestStatus_TV.text = "There are no photos for this title"


    }
}