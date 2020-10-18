package edu.uoc.pac2

import android.app.Application
import android.util.Log
import edu.uoc.pac2.data.*

/**
 * Entry point for the Application.
 */
class MyApplication : Application() {

    private lateinit var booksInteractor: BooksInteractor

    override fun onCreate() {
        super.onCreate()

        Log.i("Application 1", "MyApplication" )
        // TODO: Init Room Database
        // TODO: Init BooksInteractor
    }

    fun getBooksInteractor(): BooksInteractor {
        return booksInteractor
    }

    fun hasInternetConnection(): Boolean {
        // TODO: Add Internet Check logic.
        return true
    }
}