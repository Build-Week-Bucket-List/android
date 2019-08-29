package com.rybarstudios.bucketlist.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import com.rybarstudios.bucketlist.R
import com.rybarstudios.bucketlist.model.BucketItem
import kotlinx.android.synthetic.main.activity_journal.*

class JournalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal)

        var bundle: Bundle? = intent.extras
            var journalTitle = bundle?.getString("edit_title")
            et_title.setText(journalTitle)
            var journalEntry = bundle?.getString("edit_entry")
            et_entry.setText(journalEntry)
            var position = bundle?.getInt("position")



        button_save_journal.setOnClickListener {
            val title = et_title.text.toString()
            val entry = et_entry.text.toString()

            val intent = Intent()
            intent.putExtra("title", title)
            intent.putExtra("entry", entry)
            intent.putExtra("position", position)
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }

        button_delete_entry.setOnClickListener {
            val intent = Intent()
            intent.putExtra("position", position)
            setResult(Activity.RESULT_FIRST_USER, intent)
            finish()
        }


    }
}
