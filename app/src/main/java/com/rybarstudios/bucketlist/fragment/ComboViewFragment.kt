package com.rybarstudios.bucketlist.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.rybarstudios.bucketlist.R
import com.rybarstudios.bucketlist.activity.BucketListFragmentActivity
import com.rybarstudios.bucketlist.model.BucketItem
import kotlinx.android.synthetic.main.fragment_combo_view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ComboViewDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ComboViewDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ComboViewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: ComboViewOnFragmentInteractionListener? = null

    var item: BucketItem? = null

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
        return inflater.inflate(R.layout.fragment_combo_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = arguments?.getSerializable(BucketListFragmentActivity.FRAGMENT_KEY) as BucketItem

        /*for (i in 0 until item.imageUri.size) {
            horizontal_scroll_view_linear_layout.addView(generateImageView(item.imageUri[i]), i)
        }

        for (i in 0 until 4) {
            combo_view_scroll_view_linear_layout.addView(generateTextView(item.journalEntryTitle[i]))
        }*/


    }

    private fun generateImageView(imageSrc: Uri) : ImageView {
        val imageView = ImageView(context)
        imageView.setImageURI(imageSrc)
        return imageView
    }

    private fun generateTextView(title: String) : TextView {
        val textView = TextView(context)
        textView.text = title
        textView.textSize = 18f
        textView.gravity = Gravity.CENTER

        return textView
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(item: BucketItem) {
        listener?.onComboViewFragmentInteraction(item)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ComboViewOnFragmentInteractionListener) {
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
    interface ComboViewOnFragmentInteractionListener {
        fun onComboViewFragmentInteraction(item: BucketItem)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ComboViewDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ComboViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
