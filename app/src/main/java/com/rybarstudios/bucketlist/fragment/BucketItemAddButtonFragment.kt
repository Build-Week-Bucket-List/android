package com.rybarstudios.bucketlist.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.rybarstudios.bucketlist.R
import com.rybarstudios.bucketlist.model.BucketItem
import kotlinx.android.synthetic.main.fragment_bucket_item_add_button.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [button.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [button.newInstance] factory method to
 * create an instance of this fragment.
 */
class BucketItemAddButtonFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnBucketItemAddButtonFragmentInteractionListener? = null

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
        return inflater.inflate(R.layout.fragment_bucket_item_add_button, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_add_item.setOnClickListener {
            Toast.makeText(context, "Add Bucket List Item", Toast.LENGTH_SHORT).show()

            // Send blank data to the BucketItemAddFragment listener
            listener?.onBucketItemAddButtonFragmentInteraction(BucketItem("", "", ArrayList(), false, 0))
        }
    }

    fun onButtonPressed(item: BucketItem) {
        listener?.onBucketItemAddButtonFragmentInteraction(item)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBucketItemAddButtonFragmentInteractionListener) {
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
    interface OnBucketItemAddButtonFragmentInteractionListener {
        fun onBucketItemAddButtonFragmentInteraction(item: BucketItem)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment button.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BucketItemAddButtonFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}