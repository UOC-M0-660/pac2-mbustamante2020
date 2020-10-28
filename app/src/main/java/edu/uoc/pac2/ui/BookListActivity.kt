package edu.uoc.pac2.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.uoc.pac2.MyApplication
import edu.uoc.pac2.R
import edu.uoc.pac2.data.Book
import kotlinx.coroutines.runBlocking

/**
 * An activity representing a list of Books.
 */
class BookListActivity : AppCompatActivity() {

    private val TAG = "BookListActivity"

    private lateinit var adapter: BooksListAdapter
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        // Init UI
        initToolbar()
        initRecyclerView()

        // Get Books
        getBooks()

        // TODO: Add books data to Firestore [Use once for new projects with empty Firestore Database]
        //var firestoreBookData:FirestoreBookData = edu.uoc.pac2.data.FirestoreBookData
        //firestoreBookData.addBooksDataToFirestoreDatabase()
    }

    // Init Top Toolbar
    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title
    }

    // Init RecyclerView
    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.book_list)
        // Set Layout Manager
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        // Init Adapter
        adapter = BooksListAdapter(emptyList())
        recyclerView.adapter = adapter
    }

    // TODO: Get Books and Update UI
    private fun getBooks() {
        val bookList = ArrayList<Book>()

        var conection = (application as MyApplication).hasInternetConnection(this)

        Log.i("BookListActivity", conection.toString())

        if( conection ) {
            val db = Firebase.firestore
            db.collection("books")
                    .addSnapshotListener { snapshots, e ->
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e)
                            return@addSnapshotListener
                        }

                        for (doc in snapshots!!.documentChanges) {
                            bookList.add(
                                    Book(uid = doc.document.data["uid"].toString().toInt(),
                                            author = doc.document.data["author"].toString(),
                                            description = doc.document.data["description"].toString(),
                                            publicationDate = doc.document.data["publicationDate"].toString(),
                                            title = doc.document.data["title"].toString(),
                                            urlImage = doc.document.data["urlImage"].toString()
                                    )
                            )
                        }
                        adapter.setBooks(bookList);
                        //saveBooksToLocalDatabase(bookList)

                    }
        } else {
            loadBooksFromLocalDb()
        }
    }


    // TODO: Load Books from Room
    private fun loadBooksFromLocalDb() {
        //throw NotImplementedError()
        Log.i("BookListActivity", "loadBooksFromLocalDb")
        runBlocking {
            adapter.setBooks((application as MyApplication).getBooksInteractor().getAllBooks())
        }
    }

    // TODO: Save Books to Local Storage
    private fun saveBooksToLocalDatabase(books: List<Book>) {
        //throw NotImplementedError()
        Log.i("BookListActivity", "saveBooksToLocalDatabase")
        runBlocking {
            (application as MyApplication).getBooksInteractor().saveBooks(books)
        }
    }
}