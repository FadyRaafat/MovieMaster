package com.fady.moviemaster.view.adapters.searchresult

import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fady.moviemaster.R

class MovieViewHolder( val rootView: View) : RecyclerView.ViewHolder(rootView) {
     var movieTitle: TextView = rootView.findViewById(R.id.movieTitle_TV)
    var movieYear: TextView = rootView.findViewById(R.id.movieYear_TV)
     var movieRating : RatingBar = rootView.findViewById(R.id.movieRating_RB)

}