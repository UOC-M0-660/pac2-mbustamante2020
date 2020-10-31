package edu.uoc.pac2.data

import androidx.room.*

/**
 * Book Dao (Data Access Object) for accessing Book Table functions.
 */

@Dao
interface BookDao {

    @Query("SELECT * FROM book")
    fun getAllBooks(): List<Book>

    @Query("SELECT * FROM book WHERE uid = :id")
    fun getBookById(id: Int): Book?

    @Query("SELECT * FROM book WHERE title = :titleBook")
    fun getBookByTitle(titleBook: String): Book?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveBook(book: Book): Long
}