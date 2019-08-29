package com.rybarstudios.bucketlist.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rybarstudios.bucketlist.R
import com.rybarstudios.bucketlist.fragment.BucketItemAddButtonFragment
import com.rybarstudios.bucketlist.fragment.BucketItemAddFragment
import com.rybarstudios.bucketlist.fragment.BucketItemListFragment
import com.rybarstudios.bucketlist.model.BucketItem
import com.rybarstudios.bucketlist.model.BucketListItem
import kotlinx.android.synthetic.main.fragment_bucket_item_list.*

class BucketListFragmentActivity : AppCompatActivity(),
    BucketItemAddButtonFragment.OnBucketItemAddButtonFragmentInteractionListener,
    BucketItemListFragment.OnBucketItemListFragmentInteractionListener,
    BucketItemAddFragment.OnBucketItemAddFragmentInteractionListener {

    companion object {
        const val FRAGMENT_KEY = "P98AINSDKFIUH09O12U3FIUH"
        const val FRAGMENT_KEY_2 = "P098IH90F218HQUHS0DFGUH"
        const val DETAIL_INTENT_KEY = "DETAIL_INTENT_KEY"
    }

    override fun onBucketItemListFragmentInteraction(item: BucketItem) {
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
        item.indexId = BucketListItem.bucketListItem.size
        BucketListItem.bucketListItem.add(item)
        bucket_item_list.adapter?.notifyItemInserted(item.indexId)
        if (item.indexId < 3) {
            Toast.makeText(this, "Long press to delete the new item!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bucket_list_fragment)

        BucketListItem.bucketListItem.add(BucketItem("machu pichu", "visit machu pichu", mutableListOf("one","two","three"), mutableListOf("asdfasdf", "ASDFASDFA", "2457274572574"), ArrayList(), false, 0))
        BucketListItem.bucketListItem.add(BucketItem("Marathon", "Run a full maraton", mutableListOf("one","two","three"), mutableListOf("asdfasdf", "ASDFASDFA", "2457274572574"), ArrayList(), false, 1))
        BucketListItem.bucketListItem.add(BucketItem("Skydiving", "Do a high altitude skydive", mutableListOf("one","two","three"), mutableListOf("asdfasdf", "ASDFASDFA", "2457274572574"), ArrayList(), false, 2))
        BucketListItem.bucketListItem.add(BucketItem("Giza Pyramids", "See the Great Pyramids of Giza", mutableListOf("one","two","three"), mutableListOf("asdfasdf", "ASDFASDFA", "2457274572574"), ArrayList(), false, 3))
        BucketListItem.bucketListItem.add(BucketItem("Alligator", "Wrestle an alligator to establish dominance", mutableListOf("one","two","three"), mutableListOf("asdfasdf", "ASDFASDFA", "2457274572574"), ArrayList(), false, 4))
        BucketListItem.bucketListItem.add(BucketItem("President", "Meet the President of the United States", mutableListOf("one","two","three"), mutableListOf("asdfasdf", "ASDFASDFA", "2457274572574"), ArrayList(), false, 5))
        BucketListItem.bucketListItem.add(BucketItem("Mike Tyson", "Knock Out Mike Tyson IRL", mutableListOf("one","two","three"), mutableListOf("asdfasdf", "ASDFASDFA", "2457274572574"), ArrayList(), false, 6))
        BucketListItem.bucketListItem.add(BucketItem("Go-Kart", "Hire a bunch of actors to dress up as mario characters and go go-karting", mutableListOf("one","two","three"), mutableListOf("asdfasdf", "ASDFASDFA", "2457274572574"), ArrayList(), false, 7))
        BucketListItem.bucketListItem.add(BucketItem("RATM", "Rage Against The Machine with Rage Against The Machine", mutableListOf("one","two","three"), mutableListOf("asdfasdf", "ASDFASDFA", "2457274572574"), ArrayList(), false, 8))
        BucketListItem.bucketListItem.add(BucketItem("7 Continents", "Travel to all 7 continents", mutableListOf("one","two","three"), mutableListOf("asdfasdf", "ASDFASDFA", "2457274572574"), ArrayList(), false, 9))
        BucketListItem.bucketListItem.add(BucketItem("Appalachain Trail", "Hike the entire appalachain trail", mutableListOf("one","two","three"), mutableListOf("asdfasdf", "ASDFASDFA", "2457274572574"), ArrayList(), false, 10))
        BucketListItem.bucketListItem.add(BucketItem("Tornado", "See a tornado", mutableListOf("one","two","three"), mutableListOf("asdfasdf", "ASDFASDFA", "2457274572574"), ArrayList(), false, 11))
        BucketListItem.bucketListItem.add(BucketItem("Hurrican", "Fly into a hurricane with the NHS Hurrican Chasers", mutableListOf("one","two","three"), mutableListOf("asdfasdf", "ASDFASDFA", "2457274572574"), ArrayList(), false, 12))

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
