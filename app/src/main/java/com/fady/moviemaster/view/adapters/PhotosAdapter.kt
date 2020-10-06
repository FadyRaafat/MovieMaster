package com.fady.moviemaster.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fady.moviemaster.R
import com.fady.moviemaster.datamodel.models.Photo
import com.fady.moviemaster.utilis.Constants
import kotlinx.android.synthetic.main.item_photo.view.*


class PhotosAdapter(private val photosList: List<Photo>) :
    RecyclerView.Adapter<PhotosAdapter.PhotosHolder>() {

    lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosHolder {
        context = parent.context

        val inflater = LayoutInflater.from(parent.context)
        return PhotosHolder(inflater.inflate(R.layout.item_photo, parent, false))
    }

    override fun onBindViewHolder(holder: PhotosHolder, position: Int) {
        val photo = photosList[position]
        Glide.with(context).load(urlBuilder(photo)).into(holder.photo)


    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    inner class PhotosHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photo: ImageView = view.photo_IV

    }

    override fun getItemCount(): Int {
        return photosList.size
    }

    fun urlBuilder(photo: Photo): String {
        return "http://farm" + photo.farm + ".staticflickr.com/" + photo.server + "/" + photo.id + "_" + Constants.SECRET_API_KEY + "_m.jpg"
    }


}