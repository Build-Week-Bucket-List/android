package com.rybarstudios.bucketlist.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rybarstudios.bucketlist.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        cardview_login.setOnClickListener {
            val intent = Intent(this, BucketListActivity::class.java)
            startActivity(intent)
        }
    }
}
