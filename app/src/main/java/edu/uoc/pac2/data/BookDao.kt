package edu.uoc.pac2.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * Book Dao (Data Access Object) for accessing Book Table functions.
 */

interface BookDao {
    fun getAllBooks(): List<Book>

    fun getBookById(id: Int): Book?

    fun getBookByTitle(titleBook: String): Book?

    fun saveBook(book: Book): Long

    @Query("SELECT * FROM book")
    fun getAll(): List<Book>

    @Query("SELECT * FROM book WHERE uid IN (:bookIds)")
    fun loadAllByIds(bookIds: IntArray): List<Book>

    @Query("SELECT * FROM book WHERE title LIKE :first AND " +
            "author LIKE :last LIMIT 1")
    fun findByName(title: String, author: String): Book

    @Insert
    fun insertAll(vararg book: Book)

    @Delete
    fun delete(book: Book)
}