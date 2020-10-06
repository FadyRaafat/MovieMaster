package com.fady.moviemaster.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fady.moviemaster.R
import com.fady.moviemaster.datamodel.models.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesAdapter(private val moviesList: List<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.MoviesHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MoviesHolder(inflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        val movie = moviesList[position]
        holder.movieTitle.text = movie.title
        holder.movieYear.text = "(" + movie.year.toString() + ")"
        holder.movieRating.rating = movie.rating!!.toFloat()


    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    inner class MoviesHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieTitle: TextView = view.movieTitle_TV
        val movieYear: TextView = view.movieYear_TV
        val movieRating : RatingBar = view.movieRating_RB

    }

    override fun getItemCount(): Int {
        return moviesList.size
    }


}