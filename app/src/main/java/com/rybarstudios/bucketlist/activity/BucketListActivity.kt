package com.rybarstudios.bucketlist.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class FragmentActivity : AppCompatActivity(),
    AddButtonFragment.OnButtonFragmentInteractionListener,
    ListFragment.OnListFragmentInteractionListener,
    RatingsFragment.OnRatingsFragmentInteractionListener {

    companion object {
        const val FRAGMENT_KEY = "P98AINSDKFIUH09O12U3FIUH"
        lateinit var context: Context
    }

    override fun onButtonFragmentInteraction(item: MovieItem) {
        val buttonFragment = RatingsFragment()

        val bundle = Bundle()
        bundle.putSerializable(FRAGMENT_KEY, item)
        buttonFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.ratings_fragment_holder, buttonFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onRatingsFragmentInteraction(item: MovieItem) {
        if (item.movieName == "" && item.changedBoolean) {
            // Delete the item
            // Refactor movieList to update all movieIndexPos values
        } else if (item.changedBoolean) {
            // Item was modified
            movieList[item.movieIndexPos] = item
            list_fragment.adapter?.notifyItemChanged(item.movieIndexPos)
        } else if (item.movieName != ""){
            // New item added
            item.movieIndexPos = movieList.size
            movieList.add(item)
            list_fragment.adapter?.notifyItemInserted(item.movieIndexPos)
        }
    }

    override fun onListFragmentInteraction(item: MovieItem) {
        val listFragment = RatingsFragment()

        val bundle = Bundle()
        bundle.putSerializable(FRAGMENT_KEY, item)
        listFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.ratings_fragment_holder, listFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        movieList.add(MovieItem("a;ldjsbg;pajknsd", 3, 0, false))
        movieList.add(MovieItem("a;ldjsbg;pajknsd", 4, 1, false))
        movieList.add(MovieItem("a;ldjsbg;pajknsd", 5, 2, false))
        movieList.add(MovieItem("a;ldjsbg;pajknsd", 2, 3, false))
        movieList.add(MovieItem("a;ldjsbg;pajknsd", 1, 4, false))
        movieList.add(MovieItem("a;ldjsbg;pajknsd", 5, 5, false))
        movieList.add(MovieItem("a;ldjsbg;pajknsd", 4, 6, false))
        movieList.add(MovieItem("a;ldjsbg;pajknsd", 1, 7, false))
        movieList.add(MovieItem("a;ldjsbg;pajknsd", 2, 8, false))
        movieList.add(MovieItem("a;ldjsbg;pajknsd", 3, 9, false))
        movieList.add(MovieItem("a;ldjsbg;pajknsd", 4, 10, false))
        movieList.add(MovieItem("a;ldjsbg;pajknsd", 5, 11, false))
        movieList.add(MovieItem("a;ldjsbg;pajknsd", 1, 12, false))
        movieList.add(MovieItem("a;ldjsbg;pajknsd", 2, 13, false))
        movieList.add(MovieItem("a;ldjsbg;pajknsd", 5, 14, false))
        movieList.add(MovieItem("a;ldjsbg;pajknsd", 3, 15, false))
        movieList.add(MovieItem("a;ldjsbg;pajknsd", 1, 16, false))
        movieList.add(MovieItem("a;ldjsbg;pajknsd", 2, 17, false))
        movieList.add(MovieItem("a;ldjsbg;pajknsd", 4, 18, false))

        val fragmentButton = AddButtonFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.button_fragment_holder, fragmentButton)
            .commit()

        val fragmentList = ListFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.list_fragment_holder, fragmentList)
            .commit()

        if (movieList.size == 0) {
            val fragmentRating = RatingsFragment()

            val bundle = Bundle()
            bundle.putSerializable(FRAGMENT_KEY, MovieItem("", 0, 0, false))
            fragmentRating.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.ratings_fragment_holder, fragmentRating)
                .addToBackStack(null)
                .commit()
        }
    }
}
