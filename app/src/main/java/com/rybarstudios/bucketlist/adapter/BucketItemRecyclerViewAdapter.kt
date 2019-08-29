package com.rybarstudios.bucketlist.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Animatable
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.rybarstudios.bucketlist.R
import com.rybarstudios.bucketlist.activity.BucketListFragmentActivity.Companion.DETAIL_INTENT_KEY
import com.rybarstudios.bucketlist.activity.DetailActivity
import com.rybarstudios.bucketlist.activity.DetailFragmentActivity
import com.rybarstudios.bucketlist.fragment.BucketItemListFragment
import com.rybarstudios.bucketlist.model.BucketItem
import kotlinx.android.synthetic.main.layout_bucket_item.view.*

class BucketItemRecyclerViewAdapter(
    private val data: MutableList<BucketItem>,
    private val listener: BucketItemListFragment.OnBucketItemListFragmentInteractionListener?
) : RecyclerView.Adapter<BucketItemRecyclerViewAdapter.ViewHolder>() {

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
                .inflate(R.layout.layout_bucket_item, parent, false)
        context = parent.context
        return ViewHolder(viewGroup)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = data[position].name
        holder.description.text = data[position].description

        //Starts DetailFragmentActivity when bucketListItem is clicked -- TC
        holder.itemCard.setOnClickListener {
            val detailIntent = Intent(context, DetailActivity::class.java)
            detailIntent.putExtra(DETAIL_INTENT_KEY, data[position])
            (context as Activity).startActivity(detailIntent)
        }

        //declaring avd images
        val checkImage = ContextCompat.getDrawable(context, R.drawable.check)
        val uncheckImage = ContextCompat.getDrawable(context, R.drawable.uncheck)

        //setting drawable based on completed value
        if (data[position].completed) {
            holder.image.setImageDrawable(checkImage)
        } else {
            holder.image.setImageDrawable(uncheckImage)
        }

        //listener to change animation when clicked
        holder.image.setOnClickListener {
            if (data[position].completed) {
                data[position].completed = false
                holder.image.setImageDrawable(uncheckImage)
                (uncheckImage as Animatable).start()
            } else {
                data[position].completed = true
                holder.image.setImageDrawable(checkImage)
                (checkImage as Animatable).start()
            }

        }


    }
}
