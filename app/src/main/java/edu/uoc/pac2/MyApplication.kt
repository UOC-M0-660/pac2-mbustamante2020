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

        // TODO: Init BooksInteractor
        booksInteractor = BooksInteractor(bookDao)
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
                return true
            }
        }
        return false
    }
}