package com.rybarstudios.bucketlist.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.rybarstudios.bucketlist.R
import com.rybarstudios.bucketlist.activity.FullscreenActivity
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
        holder.image.setOnClickListener {
            val intent = Intent(holder.image.context, FullscreenActivity::class.java)
            intent.putExtra("object", imageList[position].toString())
            (holder.image.context as Activity).startActivity(intent)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.recycler_view_photo_gallery_item
    }
}