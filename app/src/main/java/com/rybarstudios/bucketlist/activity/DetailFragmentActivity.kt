package com.rybarstudios.bucketlist.activity

import android.net.Uri
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rybarstudios.bucketlist.R
import com.rybarstudios.bucketlist.activity.BucketListFragmentActivity.Companion.DETAIL_INTENT_KEY
import com.rybarstudios.bucketlist.fragment.ComboViewDetailFragment
import com.rybarstudios.bucketlist.activity.BucketListFragmentActivity.Companion.FRAGMENT_KEY
import com.rybarstudios.bucketlist.activity.BucketListFragmentActivity.Companion.FRAGMENT_KEY_2
import com.rybarstudios.bucketlist.fragment.JournalItemDetailFragment
import com.rybarstudios.bucketlist.fragment.JournalItemListFragment
import com.rybarstudios.bucketlist.fragment.PhotoGalleryDetailFragment
import com.rybarstudios.bucketlist.model.BucketItem

class DetailFragmentActivity : AppCompatActivity(),
    JournalItemDetailFragment.OnJournalItemFragmentInteractionListener,
    PhotoGalleryDetailFragment.PhotoGalleryOnFragmentInteractionListener,
    JournalItemListFragment.OnJournalItemListFragmentInteractionListener,
    ComboViewDetailFragment.ComboViewOnFragmentInteractionListener{
    override fun onComboViewFragmentInteraction(item: BucketItem) {
        val listItem = PhotoGalleryDetailFragment()
        val bundle = Bundle()
        bundle.putSerializable(FRAGMENT_KEY, item)
        listItem.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.combo_view_detail_fragment, listItem)
            .addToBackStack(null)
            .commit()
    }

    override fun onJournalItemListFragmentInteraction(item: BucketItem, journalEntryIndex: Int) {
        val listItem = JournalItemDetailFragment()
        val bundle = Bundle()
        bundle.putSerializable(FRAGMENT_KEY, item)
        bundle.putSerializable(FRAGMENT_KEY_2, journalEntryIndex)
        listItem.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.journal_entry_detail_fragment, listItem)
            .addToBackStack(null)
            .commit()

    }

    override fun onJournalItemFragmentInteraction(item: BucketItem) {

    }

    override fun onPhotoGalleryFragmentInteraction(item: BucketItem) {
        val listItem = PhotoGalleryDetailFragment()
        val bundle = Bundle()
        bundle.putSerializable(FRAGMENT_KEY, item)
        listItem.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.photo_gallery_detail_fragment, listItem)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_fragment)


        val bottomNavigation: BottomNavigationView? = findViewById(R.id.nav_view)
        bottomNavigation?.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        //catch data from bucket recyclerview
        val bundle: Bundle? = intent.extras
        val bucketItem = bundle?.getSerializable(DETAIL_INTENT_KEY) as BucketItem

        //inflate journal list in onCreate
        val fragmentList = JournalItemListFragment()
        val fragmentBundle = Bundle()
        fragmentBundle.putSerializable(FRAGMENT_KEY, bucketItem)
        fragmentList.arguments = fragmentBundle

        supportFragmentManager.beginTransaction()      //this just calls fragment manager, .beginTransaction starts builder process
            .replace(R.id.bottom_nav_container, fragmentList)
            .commit()

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        var selectedFragment: Fragment? = null
        when (it.itemId) {
            R.id.navigation_combo_view -> {
                selectedFragment = ComboViewDetailFragment()
            }
            R.id.navigation_journal_entries -> {
                selectedFragment = JournalItemDetailFragment()
            }
            R.id.navigation_photo_gallery -> {
                selectedFragment = PhotoGalleryDetailFragment()
            }
        }
        selectedFragment?.let { it1 ->
            supportFragmentManager.beginTransaction().replace(R.id.bottom_nav_container,
                it1
            ).commit()
        }
        true
    }

}
