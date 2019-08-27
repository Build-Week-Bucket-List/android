package com.rybarstudios.bucketlist.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.ListFragment
import com.rybarstudios.bucketlist.R
import com.rybarstudios.bucketlist.fragment.AddButtonFragment
import com.rybarstudios.bucketlist.fragment.BucketListItemFragment
import com.rybarstudios.bucketlist.model.BucketItem

class BucketListActivity : AppCompatActivity(),
    AddButtonFragment.OnButtonFragmentInteractionListener,
    BucketListItemFragment.OnBucketItemListFragmentInteractionListener {

    companion object {
        const val FRAGMENT_KEY = "P98AINSDKFIUH09O12U3FIUH"
        lateinit var context: Context
    }

    override fun onButtonFragmentInteraction(item: BucketItem) {
        val intent = Intent(this, DetailFragmentActivity::class.java)
        startActivity(intent)
        /*val buttonFragment = RatingsFragment()

        val bundle = Bundle()
        bundle.putSerializable(FRAGMENT_KEY, item)
        buttonFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.ratings_fragment_holder, buttonFragment)
            .addToBackStack(null)
            .commit()*/
    }

    /*override fun onRatingsFragmentInteraction(item: MovieItem) {
        if (item.movieName == "" && item.changedBoolean) {
            // Delete the item
            // Refactor movieList to update all movieIndexPos values
        } else if (item.changedBoolean) {
            // Item was modified
            movieList[item.movieIndexPos] = item
            list_fragment.adapter?.notifyItemChanged(item.movieIndexPos)
        } else if (item.movieName != ""){
            // New item added
            item.movieIndexPos = movieList.size
            movieList.add(item)
            list_fragment.adapter?.notifyItemInserted(item.movieIndexPos)
        }
    }*/

    override fun onBucketItemListFragmentInteraction(item: BucketItem) {
        /*val listFragment = BucketListItemFragment()

        val bundle = Bundle()
        bundle.putSerializable(FRAGMENT_KEY, item)
        listFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.ratings_fragment_holder, listFragment)
            .addToBackStack(null)
            .commit()*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bucket_list)



        val fragmentButton = AddButtonFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.button_fragment_holder, fragmentButton)
            .commit()

        val fragmentList = BucketListItemFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.list_fragment_holder, fragmentList)
            .commit()

        /*if (movieList.size == 0) {
            val fragmentRating = RatingsFragment()

            val bundle = Bundle()
            bundle.putSerializable(FRAGMENT_KEY, MovieItem("", 0, 0, false))
            fragmentRating.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.ratings_fragment_holder, fragmentRating)
                .addToBackStack(null)
                .commit()*/
    }
}
