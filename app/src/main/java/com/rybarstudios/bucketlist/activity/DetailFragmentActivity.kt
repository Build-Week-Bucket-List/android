package com.rybarstudios.bucketlist.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Thumbnails.getThumbnail
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rybarstudios.bucketlist.R
import com.rybarstudios.bucketlist.activity.BucketListFragmentActivity.Companion.DETAIL_INTENT_KEY
import com.rybarstudios.bucketlist.activity.BucketListFragmentActivity.Companion.FRAGMENT_KEY
import com.rybarstudios.bucketlist.activity.BucketListFragmentActivity.Companion.FRAGMENT_KEY_2
import com.rybarstudios.bucketlist.fragment.*
import com.rybarstudios.bucketlist.model.BucketItem
import com.rybarstudios.bucketlist.model.BucketListItem
import com.rybarstudios.bucketlist.model.PhotoHashMap
import kotlinx.android.synthetic.main.fragment_journal.*
import kotlinx.android.synthetic.main.fragment_photo_gallery.*
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

class DetailFragmentActivity : AppCompatActivity(),
    JournalDetailFragment.OnJournalDetailFragmentInteractionListener,
    PhotoGalleryFragment.OnPhotoGalleryFragmentInteractionListener,
    JournalFragment.OnJournalFragmentInteractionListener {

    companion object {
        const val IMAGE_REQUEST_CODE = 9878
        const val DETAIL_FRAGMENT_TAG = "9HASOPUDHF09U1QHFR"
    }

    private var bucketItemTop: BucketItem? = null

    override fun onJournalFragmentInteraction(item: BucketItem, journalEntryIndex: Int) {
        val journalEntry = JournalDetailFragment()
        val bundle = Bundle()
        bundle.putSerializable(FRAGMENT_KEY, item)
        bundle.putSerializable(FRAGMENT_KEY_2, journalEntryIndex)
        journalEntry.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.detail_fragment_holder, journalEntry, DETAIL_FRAGMENT_TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun onJournalDetailFragmentInteraction(item: BucketItem?) {

    }

    override fun onPhotoGalleryFragmentInteraction(item: BucketItem, imageIndex: Int) {
        if (imageIndex == -5346) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count != 0) {
            val fragment =
                supportFragmentManager.findFragmentByTag(DETAIL_FRAGMENT_TAG) as JournalDetailFragment
            fragment.onBackButtonPressed()
            journal_item_list.adapter?.notifyDataSetChanged()
        } else {
            checkForEditedBucketItemTitle()
            checkForEditedBucketItemDescription()
            super.onBackPressed()
        }
    }

    private fun checkForEditedBucketItemDescription() {
        if (bucket_list_item_description.text.toString() != bucketItemTop?.description) {
            BucketListItem.bucketListItem[bucketItemTop!!.indexId].description =
                bucket_list_item_description.text.toString()
            bucketItemTop!!.description = bucket_list_item_description.text.toString()
        }
    }

    private fun checkForEditedBucketItemTitle() {
        if (bucket_list_item_name.text.toString() != bucketItemTop?.name) {
            BucketListItem.bucketListItem[bucketItemTop!!.indexId].name =
                bucket_list_item_name.text.toString()
            bucketItemTop!!.name = bucket_list_item_name.text.toString()
        }
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
        fragmentBundle.putSerializable(
            FRAGMENT_KEY,
            BucketListItem.bucketListItem[bucketItem.indexId]
        )
        fragmentList.arguments = fragmentBundle

        //this just calls fragment manager, .beginTransaction starts builder process
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_holder, fragmentList)
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val photoUri: Uri? = data?.data
            val bitmapImage = MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)
            if (photoUri != null && bitmapImage != null) {

                // Make a new HashMap value, load the content into it, and then add it to
                PhotoHashMap.photoHashMap.put(photoUri.toString(), bitmapImage)
                //PhotoHashMap.photoHashMap.add(hashMap)

                BucketListItem.bucketListItem[bucketItemTop!!.indexId].imageUri.add(photoUri.toString())

                // Load the new image into view
                photo_gallery_detail_recycler_view.adapter?.notifyDataSetChanged()
            }
        }
    }
/*
    // https://stackoverflow.com/questions/3879992/how-to-get-bitmap-from-an-uri/6228188#6228188
    @Throws(FileNotFoundException::class, IOException::class)
    private fun getThumbnail(uri: Uri?): Bitmap? {
        if (uri != null) {
            var input = this.contentResolver.openInputStream(uri)

            val onlyBoundsOptions = BitmapFactory.Options()
            onlyBoundsOptions.inJustDecodeBounds = true
            onlyBoundsOptions.inDither = true
            onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888
            BitmapFactory.decodeStream(input, null, onlyBoundsOptions)
            input?.close()

            if (onlyBoundsOptions.outWidth == -1 || onlyBoundsOptions.outHeight == -1) {
                return null
            }

            val originalSize =
                if (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) onlyBoundsOptions.outHeight else onlyBoundsOptions.outWidth

            val thumbnailSize =

            val ratio = if (originalSize > THUMBNAIL_SIZE) originalSize / THUMBNAIL_SIZE else 1.0

            val bitmapOptions = BitmapFactory.Options()
            bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio)
            bitmapOptions.inDither = true
            bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888
            input = this.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions)
            input?.close()
            return bitmap
        }
        return null
    }

    // https://stackoverflow.com/questions/3879992/how-to-get-bitmap-from-an-uri/6228188#6228188
    private fun getPowerOfTwoForSampleRatio(ratio: Double): Int {
        val k = Integer.highestOneBit(Math.floor(ratio).toInt())
        return if (k == 0)
            1
        else
            k
    }
*/

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            var selectedFragment: Fragment? = null
            when (it.itemId) {
                R.id.navigation_journal_entries -> {
                    selectedFragment = JournalFragment()
                }
                R.id.navigation_photo_gallery -> {
                    selectedFragment = PhotoGalleryFragment()
                }
            }
            selectedFragment?.let { it1 ->
                val fragmentBundle = Bundle()
                fragmentBundle.putSerializable(
                    FRAGMENT_KEY,
                    BucketListItem.bucketListItem[bucketItemTop!!.indexId]
                )
                selectedFragment.arguments = fragmentBundle
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_holder,
                    it1
                ).commit()
            }
            true
        }

}
