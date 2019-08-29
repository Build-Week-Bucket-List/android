package com.rybarstudios.bucketlist.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rybarstudios.bucketlist.R
import com.rybarstudios.bucketlist.activity.BucketListFragmentActivity.Companion.DETAIL_INTENT_KEY
import com.rybarstudios.bucketlist.activity.BucketListFragmentActivity.Companion.FRAGMENT_KEY
import com.rybarstudios.bucketlist.activity.BucketListFragmentActivity.Companion.FRAGMENT_KEY_2
import com.rybarstudios.bucketlist.fragment.*
import com.rybarstudios.bucketlist.model.BucketItem
import kotlinx.android.synthetic.main.fragment_photo_gallery.*

class DetailFragmentActivity : AppCompatActivity(),
    JournalDetailFragment.OnJournalDetailFragmentInteractionListener,
    PhotoGalleryFragment.OnPhotoGalleryFragmentInteractionListener,
    JournalFragment.OnJournalFragmentInteractionListener,
    ComboViewFragment.ComboViewOnFragmentInteractionListener,
    PhotoDetailFragment.OnPhotoDetailFragmentInteractionListener {

    companion object {
        const val IMAGE_REQUEST_CODE = 9878
    }

    private var bucketItemTop: BucketItem? = null

    override fun onJournalFragmentInteraction(item: BucketItem, journalEntryIndex: Int) {
        val journalEntry = JournalDetailFragment()
        val bundle = Bundle()
        bundle.putSerializable(FRAGMENT_KEY, item)
        bundle.putSerializable(FRAGMENT_KEY_2, journalEntryIndex)
        journalEntry.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.detail_fragment_holder, journalEntry)
            .addToBackStack(null)
            .commit()
    }

    override fun onJournalDetailFragmentInteraction(item: BucketItem) {

    }

    override fun onPhotoGalleryFragmentInteraction(item: BucketItem, imageIndex: Int) {
        if (imageIndex == -5346) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        } else {
            val imageItem = PhotoDetailFragment()

            val bundle = Bundle()
            bundle.putSerializable(FRAGMENT_KEY, item)
            bundle.putSerializable(FRAGMENT_KEY_2, imageIndex)
            imageItem.arguments = bundle

            supportFragmentManager.beginTransaction()
                .replace(R.id.detail_fragment_holder, imageItem)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onPhotoDetailFragmentInteraction(item: BucketItem, photoIndex: Int) {

    }

    override fun onComboViewFragmentInteraction(item: BucketItem) {
        val listItem = PhotoGalleryFragment()

        val bundle = Bundle()
        bundle.putSerializable(FRAGMENT_KEY, item)
        listItem.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_holder, listItem)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_fragment)

        //catch data from bucket recyclerview
        val bundle: Bundle? = intent.extras
        val bucketItem = bundle?.getSerializable(DETAIL_INTENT_KEY) as BucketItem
        bucketItemTop = bucketItem

        val bottomNavigation: BottomNavigationView? = findViewById(R.id.nav_view)
        bottomNavigation?.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        //inflate journal list in onCreate
        val fragmentList = JournalFragment()
        val fragmentBundle = Bundle()
        fragmentBundle.putSerializable(FRAGMENT_KEY, bucketItemTop)
        fragmentList.arguments = fragmentBundle

        supportFragmentManager.beginTransaction()      //this just calls fragment manager, .beginTransaction starts builder process
            .replace(R.id.fragment_holder, fragmentList)
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val photoUri: Uri? = data?.data
            if (photoUri != null) {
                bucketItemTop?.imageUri?.add(photoUri.toString())
                photo_gallery_detail_recycler_view.adapter?.notifyDataSetChanged()
            }
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        var selectedFragment: Fragment? = null
        when (it.itemId) {
            R.id.navigation_combo_view -> {
                selectedFragment = ComboViewFragment()
            }
            R.id.navigation_journal_entries -> {
                selectedFragment = JournalFragment()
            }
            R.id.navigation_photo_gallery -> {
                selectedFragment = PhotoGalleryFragment()
            }
        }
        selectedFragment?.let { it1 ->
            val fragmentBundle = Bundle()
            fragmentBundle.putSerializable(FRAGMENT_KEY, bucketItemTop)
            selectedFragment.arguments = fragmentBundle
            supportFragmentManager.beginTransaction().replace(R.id.fragment_holder,
                it1
            ).commit()
        }
        true
    }

}
