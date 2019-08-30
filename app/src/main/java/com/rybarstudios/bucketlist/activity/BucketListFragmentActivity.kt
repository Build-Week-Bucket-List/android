package com.rybarstudios.bucketlist.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        // If the data hasn't been loaded before when going from LoginActivity to
        // BucketListFragmentActivity
        if (BucketListItem.bucketListItem.size < 1) {
            // Pre-populated dummy data to show off functionality
            BucketListItem.bucketListItem.add(
                BucketItem(
                    "HODL Bitcoin",
                    "#StackSats until I die",
                    mutableListOf(
                        "Use @bisq_network"
                    ),
                    mutableListOf(
                        "Use Bisq to avoid Know Your Customer/Anti-Money Laundering (KYC/AML) which are" +
                                " invasive and destroy your financial privacy.\n\nThe legacy financial system" +
                                " relies on surveillance, but has been taken over by the advertisement" +
                                " industry and is now bought and shared between companies.\n\nNip it in the" +
                                " bud by using cash, and acquiring Bitcoin via Bisq where you share as" +
                                " little information as is needed."
                    ),
                    ArrayList(),
                    false,
                    0
                )
            )
            BucketListItem.bucketListItem.add(
                BucketItem(
                    "Hyperbitcoinization",
                    "Live long enough to see it happen by eating healthy (The Carnivore Diet)",
                    mutableListOf(
                        "Hyperbitcoinization?",
                        "Carnivore Diet?"
                    ),
                    mutableListOf(
                        "Magic internet money (Bitcoin) deprecates that dirty Fiat money",
                        "Eat Meat, nothing else."
                    ),
                    ArrayList(),
                    false,
                    1
                )
            )
        }

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


    override fun onResume() {
        bucket_item_list.adapter?.notifyDataSetChanged()
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.share_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share_bucket_item_list) {
            showInfo()
        }
        return true
    }

    private fun showInfo() {
        val dialogTitle = "Share bucket item list"
        val dialogMessage = "Are you sure you want to share your list?"
        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
            .setMessage(dialogMessage)
            .setPositiveButton("Yes") { dialog, which ->
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, getBucketListItems())
                intent.type = "text/plain"
                startActivity(intent)
            }
            .setNegativeButton("No") { _, _ -> }
            .create()
            .show()
    }

    private fun getBucketListItems(): String {
        var bucketListItems = "My bucket list: "
        for (i in 0 until BucketListItem.bucketListItem.size) {
            bucketListItems += BucketListItem.bucketListItem[i].name + ", "
        }
        return bucketListItems

    }
}
