package com.rybarstudios.bucketlist.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.rybarstudios.bucketlist.R
import com.rybarstudios.bucketlist.activity.BucketListFragmentActivity.Companion.FRAGMENT_KEY
import com.rybarstudios.bucketlist.adapter.JournalItemRecyclerViewAdapter
import com.rybarstudios.bucketlist.model.BucketItem
import com.rybarstudios.bucketlist.model.BucketListItem
import kotlinx.android.synthetic.main.fragment_journal.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [JournalListItemFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [JournalListItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JournalFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnJournalFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_journal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = arguments?.getSerializable(FRAGMENT_KEY) as BucketItem

        bucket_list_item_name.setText(item.name)
        bucket_list_item_description.setText(item.description)

        button_save_changes.setOnClickListener {
            BucketListItem.bucketListItem[item.indexId].name = bucket_list_item_name.text.toString()
            BucketListItem.bucketListItem[item.indexId].description = bucket_list_item_description.text.toString()
        }

        journal_item_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = JournalItemRecyclerViewAdapter(item, listener!!)
        }

        button_add_journal_entry.setOnClickListener {
            val journalEntryIndex = item.journalEntryTitle.size
            item.journalEntryTitle.add("New Entry")
            item.journalEntry.add("")
            journal_item_list.adapter?.notifyItemInserted(journalEntryIndex)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnJournalFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnJournalFragmentInteractionListener {
        fun onJournalFragmentInteraction(item: BucketItem, journalEntryIndex: Int)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment JournalListItemFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JournalFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
