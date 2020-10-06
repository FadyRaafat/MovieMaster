package com.fady.moviemaster.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fady.moviemaster.R
import com.fady.moviemaster.datamodel.models.Movie
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlinx.android.synthetic.main.item_year.view.*

class SearchResultAdapter(private val moviesList: List<Movie>, private val yearsList: List<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_MOVIE = 0
    private val VIEW_TYPE_YEAR = 1
    var totalListAmount = moviesList.size + yearsList.size
    var currentYearPosition = 0



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View
        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == VIEW_TYPE_MOVIE) {
            itemView = inflater.inflate(R.layout.item_movie, parent, false)
            MoviesHolder(itemView)
        } else {
            itemView = inflater.inflate(R.layout.item_year, parent, false)
            YearHolder(itemView)

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MoviesHolder) {
            val movie = moviesList[position]
            holder.movieTitle.text = movie.title
            holder.movieYear.text = movie.year.toString()

        }
        if (holder is YearHolder) {
            val year = yearsList[currentYearPosition]
            currentYearPosition++
            holder.year.text = year

        }

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0 || moviesList[position].year != moviesList[position - 1].year ) {
            VIEW_TYPE_YEAR
        } else {
            VIEW_TYPE_MOVIE
        }

    }


    inner class MoviesHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieTitle: TextView = view.movieTitle_TV
        val movieYear: TextView = view.movieYear_TV

    }

    inner class YearHolder(view: View) : RecyclerView.ViewHolder(view) {
        val year: TextView = view.categoryYear_TV

    }


    override fun getItemCount(): Int {
        return totalListAmount
    }


}