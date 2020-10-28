package edu.uoc.pac2.data

import androidx.room.*

/**
 * Book Dao (Data Access Object) for accessing Book Table functions.
 */

@Dao
interface BookDao {

    @Query("SELECT * FROM book")
    suspend fun getAllBooks(): List<Book>

    @Query("SELECT * FROM book WHERE uid = :id")
    suspend fun getBookById(id: Int): Book?

    @Query("SELECT * FROM book WHERE title = :titleBook")
    suspend fun getBookByTitle(titleBook: String): Book?

    @Insert
    suspend fun saveBook(book: Book): Long
}