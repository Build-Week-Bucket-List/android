package com.rybarstudios.bucketlist.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.rybarstudios.bucketlist.R
import com.rybarstudios.bucketlist.fragment.JournalFragment
import com.rybarstudios.bucketlist.model.BucketItem
import com.rybarstudios.bucketlist.model.BucketListItem
import kotlinx.android.synthetic.main.layout_journal_item.view.*

class JournalItemRecyclerViewAdapter(
    private val data: BucketItem,
    private val listener: JournalFragment.OnJournalFragmentInteractionListener
) : RecyclerView.Adapter<JournalItemRecyclerViewAdapter.ViewHolder>() {

    private val journalTitle = data.journalEntryTitle
    lateinit var context: Context

    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     *
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     *
     * The new ViewHolder will be used to display items of the adapter using
     * [.onBindViewHolder]. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary [View.findViewById] calls.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     * @see .getItemViewType
     * @see .onBindViewHolder
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewGroup =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_journal_item, parent, false)
        context = parent.context
        return ViewHolder(viewGroup)

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return journalTitle.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [android.widget.ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [ViewHolder.getAdapterPosition] which will
     * have the updated adapter position.
     *
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolder, journalEntryIndex: Int) {
        // Set Journal CardView's name ET field
        holder.name.text = journalTitle[journalEntryIndex]

        // On Click Listener for that journal entry
        holder.journalCard.setOnClickListener {
            listener.onJournalFragmentInteraction(data, journalEntryIndex)
        }

        holder.journalCard.setOnLongClickListener {

            val builder = AlertDialog.Builder(context)

            builder.setTitle("Delete Item")
            builder.setMessage("Are you sure you want to delete this journal entry")
            builder.setPositiveButton("YES"){ dialog, which ->
                // Toast.makeText(context, "Image Deleted", Toast.LENGTH_SHORT).show()
                BucketListItem.bucketListItem[data.indexId].journalEntryTitle.removeAt(journalEntryIndex)
                BucketListItem.bucketListItem[data.indexId].journalEntry.removeAt(journalEntryIndex)
                notifyDataSetChanged()
            }

            builder.setNegativeButton("No"){_, _ -> }

            val dialog: AlertDialog = builder.create()
            dialog.show()

            true
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.journal_entry_title
        val journalCard: CardView = view.journal_parent_card
    }
}