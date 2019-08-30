package com.rybarstudios.bucketlist.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rybarstudios.bucketlist.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        cardview_login.setOnClickListener {
            val intent = Intent(this, BucketListFragmentActivity::class.java)
            startActivity(intent)
        }

        textview_register.setOnClickListener {
            val url = "https://build-week-bucket-list.github.io/Marketing-page/index.html"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}
