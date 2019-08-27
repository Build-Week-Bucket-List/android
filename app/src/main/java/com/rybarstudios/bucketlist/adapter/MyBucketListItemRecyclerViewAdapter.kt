package com.rybarstudios.bucketlist.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.rybarstudios.bucketlist.R
import com.rybarstudios.bucketlist.fragment.BucketListItemFragment
import com.rybarstudios.bucketlist.model.BucketItem
import kotlinx.android.synthetic.main.bucket_list_item_layout.view.*

class MyBucketListItemRecyclerViewAdapter(
    private val data: MutableList<BucketItem>,
    private val listener: BucketListItemFragment.OnBucketItemListFragmentInteractionListener?
) : RecyclerView.Adapter<MyBucketListItemRecyclerViewAdapter.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.list_item_title
        val description: TextView = view.list_item_description
        val image: ImageView = view.checkmark
        val itemCard: CardView = view.cardview_bucketlistitem
    }

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as BucketItem
            listener?.onBucketItemListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewGroup =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.bucket_list_item_layout, parent, false)
        context = parent.context
        return ViewHolder(viewGroup)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = data[position].name
        holder.description.text = data[position].description
        holder.itemCard.setOnClickListener {
            if (listener != null) {
                listener.onBucketItemListFragmentInteraction(data[position])
            }

            Toast.makeText(context, "${data[position].indexId}", Toast.LENGTH_SHORT).show()
        }


    }
}
