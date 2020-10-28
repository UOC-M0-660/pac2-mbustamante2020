package edu.uoc.pac2

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import edu.uoc.pac2.data.ApplicationDatabase
import edu.uoc.pac2.data.BookDao
import edu.uoc.pac2.data.BooksInteractor


/**
 * Entry point for the Application.
 */
class MyApplication : Application() {

    private lateinit var booksInteractor: BooksInteractor
    private lateinit var bookDao: BookDao

    override fun onCreate() {
        super.onCreate()


        // TODO: Init Room Database
        bookDao = ApplicationDatabase.getInstance(this).bookDao()
        Log.i("MyApplication", "bookDao")

        // TODO: Init BooksInteractor
        booksInteractor = BooksInteractor(bookDao)

        Log.i("MyApplication", "booksInteractor")
    }

    fun getBooksInteractor(): BooksInteractor {
        return booksInteractor
    }

    fun hasInternetConnection(context: Context): Boolean {
        // TODO: Add Internet Check logic.

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}