package com.rybarstudios.bucketlist.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.rybarstudios.bucketlist.R
import com.rybarstudios.bucketlist.activity.BucketListFragmentActivity.Companion.FRAGMENT_KEY
import com.rybarstudios.bucketlist.activity.BucketListFragmentActivity.Companion.FRAGMENT_KEY_2
import com.rybarstudios.bucketlist.model.BucketItem
import com.rybarstudios.bucketlist.model.BucketListItem
import kotlinx.android.synthetic.main.activity_detail_fragment.*
import kotlinx.android.synthetic.main.fragment_journal.*
import kotlinx.android.synthetic.main.fragment_journal_detail.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [JournalItemDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [JournalItemDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JournalDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnJournalDetailFragmentInteractionListener? = null
    private var originalTitle = ""
    private var originalEntry = ""
    private var bucketItem: BucketItem? = null
    private var entryIndex: Int? = null

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
        return inflater.inflate(R.layout.fragment_journal_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // pull in data sent from DetailFragmentActivity
        val item = arguments?.getSerializable(FRAGMENT_KEY) as BucketItem
        val journalEntryIndex = arguments?.getSerializable(FRAGMENT_KEY_2) as Int
        bucketItem = item
        entryIndex = journalEntryIndex
        originalTitle = item.journalEntryTitle[journalEntryIndex]
        originalEntry = item.journalEntry[journalEntryIndex]

        // Logic for if the user is editing or creating a new entry, will focus on different
        // EditText fields
        if (item.journalEntryTitle[journalEntryIndex] != "New Entry") {
            (context as Activity).et_journal_title.setText(item.journalEntryTitle[journalEntryIndex])
            et_journal_entry.requestFocus()
        } else {
            openSoftKeyboard(context, et_journal_title)
        }
        (context as Activity).et_journal_entry.setText(item.journalEntry[journalEntryIndex])
    }

    // Override back button
    fun onBackButtonPressed() {
        if (et_journal_title.text.toString() != originalTitle
            || et_journal_entry.text.toString() != originalEntry) {
            BucketListItem.bucketListItem[bucketItem!!.indexId]
                .journalEntryTitle[entryIndex!!] = et_journal_title.text.toString()
            BucketListItem.bucketListItem[bucketItem!!.indexId]
                .journalEntry[entryIndex!!] = et_journal_entry.text.toString()
            //journal_item_list.adapter?.notifyDataSetChanged()
        }
        // remove the fragment from view and popBackStack
        activity?.supportFragmentManager?.beginTransaction()
            ?.remove(this)
            ?.commit()
        activity?.supportFragmentManager?.popBackStack()
    }

    private fun openSoftKeyboard(context: Context?, view: View) {
        view.requestFocus()
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(item: BucketItem) {
        listener?.onJournalDetailFragmentInteraction(item)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnJournalDetailFragmentInteractionListener) {
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
    interface OnJournalDetailFragmentInteractionListener {
        fun onJournalDetailFragmentInteraction(item: BucketItem?)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment JournalItemDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JournalDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
