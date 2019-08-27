package com.rybarstudios.bucketlist.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.rybarstudios.bucketlist.R
import kotlinx.android.synthetic.main.photo_gallery_detail_item.view.*

class PhotoGalleryDetailAdapter(val imageList: MutableList<Uri>) : RecyclerView.Adapter<PhotoGalleryDetailAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_gallery_detail_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = imageList[position]
        holder.image.setImageURI(image)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.recycler_view_photo_gallery_item
    }
}