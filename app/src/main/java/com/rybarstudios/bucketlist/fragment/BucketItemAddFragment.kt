package com.rybarstudios.bucketlist.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.rybarstudios.bucketlist.R
import com.rybarstudios.bucketlist.activity.BucketListFragmentActivity.Companion.FRAGMENT_KEY
import com.rybarstudios.bucketlist.model.BucketItem
import kotlinx.android.synthetic.main.fragment_bucket_item_add.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BucketItemAddFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BucketItemAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BucketItemAddFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnBucketItemAddFragmentInteractionListener? = null

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
        return inflater.inflate(R.layout.fragment_bucket_item_add, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(item: BucketItem) {
        listener?.onBucketItemAddFragmentInteraction(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = arguments?.getSerializable(FRAGMENT_KEY) as BucketItem

        et_bucket_list_name.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                et_bucket_list_description.requestFocus()
                return@OnKeyListener true
            }
            false
        })

        // User input is saved upon pressing the enter key while in the description box
        et_bucket_list_description.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                // Bucket List Name is required
                if (et_bucket_list_name.text.toString() != "") {
                    item.name = et_bucket_list_name.text.toString()
                    item.description = et_bucket_list_description.text.toString()
                    listener?.onBucketItemAddFragmentInteraction(item)
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.remove(this)
                        ?.commit()
                } else {
                    // Notify user they cannot save content w/o adding a title
                    Toast.makeText(context, "A title is required to add an item", Toast.LENGTH_SHORT).show()
                }

            }
            false
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBucketItemAddFragmentInteractionListener) {
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
    interface OnBucketItemAddFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onBucketItemAddFragmentInteraction(item: BucketItem)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BucketItemAddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BucketItemAddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
