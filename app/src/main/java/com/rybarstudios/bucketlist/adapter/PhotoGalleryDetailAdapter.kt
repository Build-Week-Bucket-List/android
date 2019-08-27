package com.rybarstudios.bucketlist.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rybarstudios.bucketlist.R
import kotlinx.android.synthetic.main.activity_bucket_list_fragment.*

class PhotoGalleryDetailAdapter(val imageList: MutableList<Uri>) : RecyclerView.Adapter<PhotoGalleryDetailAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_photo_gallery_detail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val
    }
}