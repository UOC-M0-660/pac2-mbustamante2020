package edu.uoc.pac2.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.core.widget.NestedScrollView
import edu.uoc.pac2.R
import kotlinx.android.synthetic.main.activity_book_detail.*

/**
 * An activity representing a single Book detail screen.
 */
class BookDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        setSupportActionBar(detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
           /* val itemID = intent.getIntExtra(BookDetailFragment.ARG_ITEM_ID, -1)
            val fragment = BookDetailFragment.newInstance(itemID)

            supportFragmentManager.beginTransaction()
                    .add(R.id.frameLayout, fragment)
                    .commit()*/

            /*
            val arguments = Bundle()
            arguments.putString(BookDetailFragment.ARG_ITEM_ID, intent.getStringExtra(BookDetailFragment.ARG_ITEM_ID))

            val fragment = BookDetailFragment()
            fragment.arguments = arguments
            supportFragmentManager.beginTransaction()
                    .add(R.id.book_detail_container, fragment)
                    .commit()
                    */

            val itemID = intent.getIntExtra(BookDetailFragment.ARG_ITEM_ID, -1)
            val fragment = BookDetailFragment.newInstance(itemID)

            Log.i("fragment act", "$itemID")

            supportFragmentManager.beginTransaction()
                    .add(R.id.book_detail_container, fragment)
                    .commit()
        }



        /* fab.setOnClickListener { view ->
     //Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
     //        .setAction("Action", null).show()

     val intent = Intent().apply {
         this.action = Intent.ACTION_SEND
         this.putExtra(Intent.EXTRA_TEXT, "Hey Check out this Great app:")
         this.type="text/plain"

     }
     startActivity(intent)
     //startActivity(Intent.createChooser(intent, "Share To:"))

 }*/
    }

    // TODO: Override finish animation for actionbar back arrow
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpTo(this, Intent(this, BookListActivity::class.java))
                overridePendingTransition(R.anim.translate_in_bottom, R.anim.translate_in_top)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // TODO: Override finish animation for phone back button
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.translate_out_bottom, R.anim.translate_out_top)
    }
}