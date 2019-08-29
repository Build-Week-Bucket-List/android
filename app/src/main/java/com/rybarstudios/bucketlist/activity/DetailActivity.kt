package com.rybarstudios.bucketlist.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.rybarstudios.bucketlist.R
import com.rybarstudios.bucketlist.activity.BucketListFragmentActivity.Companion.DETAIL_INTENT_KEY
import com.rybarstudios.bucketlist.fragment.PhotoGalleryDetailFragment
import com.rybarstudios.bucketlist.model.BucketItem
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object{
        const val IMAGE_REQUEST_CODE = 2
        const val JOURNAL_REQUEST_CODE = 1
        const val JOURNAL_EDIT_CODE = 3
    }

    lateinit var bucketItem: BucketItem


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        bucketItem = intent.getSerializableExtra(DETAIL_INTENT_KEY) as BucketItem

        tv_bucket_name.text = bucketItem.name

        button_add_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }

        button_add_text.setOnClickListener {
            val intent = Intent(this, JournalActivity::class.java)
            startActivityForResult(intent, JOURNAL_REQUEST_CODE)
        }

        button_delete.setOnClickListener {
            //finish figuring out how to delete bucketlist item here
            finish()
        }
    }

    /**
     * Dispatch incoming result to the correct fragment.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val photoUri: Uri? = data?.data
            if (photoUri != null) {
                bucketItem.imageUri.add(photoUri)
            }
        }

        if(requestCode == JOURNAL_REQUEST_CODE && resultCode == Activity.RESULT_CANCELED){
            val journalEntry: String? = data?.getStringExtra("entry")
            val journalTitle: String? = data?.getStringExtra("title")

            if( journalEntry != null && journalTitle != null) {
                bucketItem.journalEntryTitle.add(journalTitle)
                bucketItem.journalEntry.add(journalEntry)
            }
        }

        if(requestCode == JOURNAL_EDIT_CODE && resultCode == Activity.RESULT_CANCELED){
            val journalEntry: String? = data?.getStringExtra("entry")
            val journalTitle: String? = data?.getStringExtra("title")
            val journalPosition: Int = data!!.getIntExtra("position", 0)

            if( journalEntry != null && journalTitle != null) {
                bucketItem.journalEntryTitle[journalPosition] = journalTitle
                bucketItem.journalEntry[journalPosition] = journalEntry
            }
        }

        if(requestCode == JOURNAL_EDIT_CODE && resultCode == Activity.RESULT_FIRST_USER){
            val journalPosition: Int = data!!.getIntExtra("position", 1000)

            bucketItem.journalEntryTitle.removeAt(journalPosition)
            bucketItem.journalEntry.removeAt(journalPosition)
        }
    }

    private fun generateImageView(imageSrc: Uri, i: Int) : ImageView {
        val imageView = ImageView(this)
        imageView.setImageURI(imageSrc)
        imageView.adjustViewBounds = true
        imageView.setOnLongClickListener {
            bucketItem.imageUri.removeAt(i)

            image_ll.removeAllViews()

            for(i in 0 until bucketItem.imageUri.size) {
                image_ll.addView(generateImageView(bucketItem.imageUri[i], i))
            }

            Toast.makeText(this, "Image Deleted", Toast.LENGTH_SHORT).show()

            true
        }
        return imageView
    }

    private fun generateTextView(title: String, i: Int) : TextView {
        val textView = TextView(this)
        textView.text = title
        textView.textSize = 18f
        textView.gravity = Gravity.CENTER
        textView.background = ContextCompat.getDrawable(this, R.color.colorAccent)
        textView.setOnClickListener {
            val intent = Intent(this, JournalActivity::class.java)
            intent.putExtra("edit_title", bucketItem.journalEntryTitle[i])
            intent.putExtra("edit_entry", bucketItem.journalEntry[i])
            intent.putExtra("position", i)
            startActivityForResult(intent, JOURNAL_EDIT_CODE)
        }

        return textView
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are *not* resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * [.onResumeFragments].
     */
    override fun onResume() {
        super.onResume()

        image_ll.removeAllViews()
        journal_ll.removeAllViews()

        for(i in 0 until bucketItem.imageUri.size) {
            image_ll.addView(generateImageView(bucketItem.imageUri[i], i))
        }

        for(i in 0 until bucketItem.journalEntry.size){
            journal_ll.addView(generateTextView(bucketItem.journalEntryTitle[i], i))
        }

    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    override fun onBackPressed() {
        super.onBackPressed()
    }
}
