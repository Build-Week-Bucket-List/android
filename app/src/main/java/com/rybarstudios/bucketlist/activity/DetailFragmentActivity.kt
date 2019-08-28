package com.rybarstudios.bucketlist.activity

import android.net.Uri
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.rybarstudios.bucketlist.R
import com.rybarstudios.bucketlist.activity.BucketListFragmentActivity.Companion.DETAIL_INTENT_KEY
import com.rybarstudios.bucketlist.fragment.JournalItemDetailFragment
import com.rybarstudios.bucketlist.fragment.PhotoGalleryDetailFragment
import com.rybarstudios.bucketlist.model.BucketItem

class DetailFragmentActivity : AppCompatActivity(),
    JournalItemDetailFragment.OnJournalItemFragmentInteractionListener,
    PhotoGalleryDetailFragment.PhotoGalleryOnFragmentInteractionListener {


    override fun onJournalItemFragmentInteraction(uri: Uri) {

    }

    override fun onPhotoGalleryFragmentInteraction(uri: Uri) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_fragment)


        val bottomNavigation: BottomNavigationView? = findViewById(R.id.nav_view)
        bottomNavigation?.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        var bundle: Bundle? = intent.extras
        var bucketItem = bundle?.getSerializable(DETAIL_INTENT_KEY) as BucketItem


    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        var selectedFragment: Fragment? = null
        when (it.itemId) {
            R.id.navigation_combo_view -> {
//                selectedFragment = TestFrag()
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
