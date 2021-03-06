package edu.uoc.pac2.ui

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import edu.uoc.pac2.R
import edu.uoc.pac2.data.ApplicationDatabase
import edu.uoc.pac2.data.Book
import edu.uoc.pac2.data.BooksInteractor
import kotlinx.android.synthetic.main.activity_book_detail.*
import kotlinx.android.synthetic.main.fragment_book_detail.*

/**
 * A fragment representing a single Book detail screen.
 * This fragment is contained in a [BookDetailActivity].
 */
class BookDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_book_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get Book for this detail screen
        loadBook()
    }


    // TODO: Get Book for the given {@param ARG_ITEM_ID} Book id
    private fun loadBook() {
        Log.i("BookDetailFragment", "39")
        if (arguments?.containsKey(ARG_ITEM_ID)!!) {
            Log.i("BookDetailFragment", "41")
            val id:Int? = ((arguments?.get(ARG_ITEM_ID) ?: -1) as Int?)
            Log.i("BookDetailFragment", "43")
            val book = BooksInteractor(context?.let { ApplicationDatabase.getInstance(it).bookDao() }!!).getBookById(id!!) as Book
            Log.i("BookDetailFragment", "45")
            initUI(book)
        }
    }

    // TODO: Init UI with book details
    private fun initUI(book: Book) {
        activity?.toolbar_layout?.title = book.title
        Picasso.with(context).load(book.urlImage).into(activity?.toolbar_image)

        book_author.text = book.author
        book_date.text = book.publicationDate
        book_detail.text = book.description
        //Picasso.with(context).load(book.urlImage).into(book_image)

        activity?.fab?.setOnClickListener {
            shareContent(book)
        }
    }

    // TODO: Share Book Title and Image URL
    private fun shareContent(book: Book) {
        val intent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT, "Title: ${book.title} \nImage URL: ${book.urlImage}")
            this.type="text/plain"
        }
        startActivity(Intent.createChooser(intent, "Share To:"))
    }

    companion object {
        /**
         * The fragment argument representing the item title that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "itemIdKey"

        fun newInstance(itemId: Int): BookDetailFragment {
            val fragment = BookDetailFragment()
            val arguments = Bundle()
            arguments.putInt(ARG_ITEM_ID, itemId)
            fragment.arguments = arguments
            return fragment
        }
    }
}