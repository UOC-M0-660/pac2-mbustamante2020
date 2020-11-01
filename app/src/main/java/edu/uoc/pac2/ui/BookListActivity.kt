package edu.uoc.pac2.ui

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import edu.uoc.pac2.MyApplication
import edu.uoc.pac2.R
import edu.uoc.pac2.data.Book
import edu.uoc.pac2.data.FirestoreBookData

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

        // Init UI
        initToolbar()
        initRecyclerView()

        // Get Books
        getBooks()

        // TODO: Add books data to Firestore [Use once for new projects with empty Firestore Database]
        /*
        var firestoreBookData:FirestoreBookData = edu.uoc.pac2.data.FirestoreBookData
        firestoreBookData.addBooksDataToFirestoreDatabase()
        */
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
        //Agrega un anuncio de publicidad con Google AdMob​
        runOnUiThread {
            MobileAds.initialize(this) {}
            mAdView = findViewById(R.id.adView)
            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)
        }

        val conection = (application as MyApplication).hasInternetConnection(this)

        //si conection es true carga los libros desde Firebase, en caso contrarios los carga desde Room
        if( conection ) {
            val db = Firebase.firestore
            var books: List<Book>?

            db.collection("books")
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        books = querySnapshot.documents.mapNotNull { it.toObject(Book::class.java) }
                        if( books == null) books = emptyList()

                        saveBooksToLocalDatabase(books!!)
                        adapter.setBooks(books!!)
                    }
                    .addOnFailureListener {
                        Log.w("Error", it.toString())
                    }
        } else {
            loadBooksFromLocalDb()
        }
    }

    // TODO: Load Books from Room
    private fun loadBooksFromLocalDb() {
        adapter.setBooks((application as MyApplication).getBooksInteractor().getAllBooks())
    }

    // TODO: Save Books to Local Storage
    private fun saveBooksToLocalDatabase(books: List<Book>) {
        AsyncTask.execute {
            (application as MyApplication).getBooksInteractor().saveBooks(books)
        }
    }
}