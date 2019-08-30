package com.rybarstudios.bucketlist.adapter

import android.app.Activity
import android.app.AlertDialog
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
import com.rybarstudios.bucketlist.activity.DetailFragmentActivity
import com.rybarstudios.bucketlist.fragment.BucketItemListFragment
import com.rybarstudios.bucketlist.model.BucketItem
import com.rybarstudios.bucketlist.model.BucketListItem
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
            val detailIntent = Intent(context, DetailFragmentActivity::class.java)
            detailIntent.putExtra(DETAIL_INTENT_KEY, data[position])
            (context as Activity).startActivity(detailIntent)
        }

        holder.itemCard.setOnLongClickListener {

            val builder = AlertDialog.Builder(context)

            builder.setTitle("Delete Item")
            builder.setMessage("Are you sure you want to delete this Bucket List Item?")
            builder.setPositiveButton("YES"){ dialog, which ->
                BucketListItem.bucketListItem.removeAt(position)
                //for loop re-assigns indices so it stays in sync when you launch the detail activity
                for(i in 0 until BucketListItem.bucketListItem.size) {
                    BucketListItem.bucketListItem[i].indexId = i
                }
                notifyDataSetChanged()
            }

            builder.setNegativeButton("No"){_, _ -> }

            val dialog: AlertDialog = builder.create()
            dialog.show()

            true
        }

        //declaring avd images
        val checkImage = ContextCompat.getDrawable(context, R.drawable.check)
        val uncheckImage = ContextCompat.getDrawable(context, R.drawable.uncheck)

        //setting drawable based on completed value
        if (data[position].completed) {
            holder.image.setImageDrawable(checkImage)
            (checkImage as Animatable).start()
        } else {
            holder.image.setImageDrawable(uncheckImage)
            (uncheckImage as Animatable).start()
        }

        //listener to change animation when clicked
        holder.image.setOnClickListener {
            if (data[position].completed == true) { //this needs the == true or it won't keep the value when you leave and re-enter the activity, idk why
                data[position].completed = false
                BucketListItem.bucketListItem[position].completed = false
                holder.image.setImageDrawable(uncheckImage)
                (uncheckImage as Animatable).start()
            } else {
                data[position].completed = true
                BucketListItem.bucketListItem[position].completed = true
                holder.image.setImageDrawable(checkImage)
                (checkImage as Animatable).start()
            }

        }


    }
}
