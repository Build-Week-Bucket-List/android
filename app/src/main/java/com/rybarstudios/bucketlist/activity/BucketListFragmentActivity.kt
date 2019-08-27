package com.rybarstudios.bucketlist.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rybarstudios.bucketlist.R
import com.rybarstudios.bucketlist.fragment.BucketItemAddButtonFragment
import com.rybarstudios.bucketlist.fragment.BucketItemAddFragment
import com.rybarstudios.bucketlist.fragment.BucketItemListFragment
import com.rybarstudios.bucketlist.model.BucketItem
import com.rybarstudios.bucketlist.model.BucketListItem.bucketListItem
import kotlinx.android.synthetic.main.fragment_bucket_item_list.*

class BucketListFragmentActivity : AppCompatActivity(),
    BucketItemAddButtonFragment.OnBucketItemAddButtonFragmentInteractionListener,
    BucketItemListFragment.OnBucketItemListFragmentInteractionListener,
    BucketItemAddFragment.OnBucketItemAddFragmentInteractionListener {

    companion object {
        const val FRAGMENT_KEY = "P98AINSDKFIUH09O12U3FIUH"
        const val DETAIL_INTENT_KEY = "DETAIL_INTENT_KEY"
    }

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

    override fun onBucketItemAddButtonFragmentInteraction(item: BucketItem) {
        val buttonFragment = BucketItemAddFragment()

        val bundle = Bundle()
        bundle.putSerializable(FRAGMENT_KEY, item)
        buttonFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.bucket_item_add_fragment_holder, buttonFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBucketItemAddFragmentInteraction(item: BucketItem) {
        item.indexId = bucketListItem.size
        bucketListItem.add(item)
        bucket_item_list.adapter?.notifyItemInserted(item.indexId)

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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bucket_list_fragment)

        // Inflate activity_bucket_list_fragment FrameLayout(s) with the fragments
        val fragmentButton = BucketItemAddButtonFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.btn_bucket_item_add_fragment_holder, fragmentButton)
            .commit()

        val fragmentList = BucketItemListFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.bucket_item_list_fragment_holder, fragmentList)
            .commit()
    }
}
