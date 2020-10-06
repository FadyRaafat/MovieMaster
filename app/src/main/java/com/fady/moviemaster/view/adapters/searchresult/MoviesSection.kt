package com.fady.moviemaster.view.adapters.searchresult

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fady.moviemaster.R
import com.fady.moviemaster.datamodel.models.Movie
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
@SuppressLint("SetTextI18n")
internal class MoviesSection(
    private val year: String,
    private val moviesList: List<Movie>,
    private val clickListener: ClickListener? = null

) : Section(
    SectionParameters.builder()
        .itemResourceId(R.layout.item_movie)
        .headerResourceId(R.layout.item_year)
        .build()
) {
    override fun getContentItemsTotal(): Int {
        return moviesList.size
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return MovieViewHolder(view)
    }

    override fun onBindItemViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val movieHolder = holder as MovieViewHolder
        val movie = moviesList[position]
        movieHolder.movieTitle.text = movie.title
        movieHolder.movieYear.text = "(" + movie.year.toString() + ")"
        movieHolder.movieRating.rating = movie.rating!!.toFloat()

        movieHolder.rootView.setOnClickListener {
            clickListener!!.onItemRootViewClicked(movie)}

    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return YearViewHolder(view)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder) {
        val headerHolder = holder as YearViewHolder
        headerHolder.tvYear.text = year
    }

    interface ClickListener {
        fun onItemRootViewClicked(
            selectedMovie: Movie
        )
    }


}