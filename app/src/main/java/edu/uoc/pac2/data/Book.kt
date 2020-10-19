package edu.uoc.pac2.data

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

/**
 * A book Model representing a piece of content.
 */

data class Book(
        //val title: String? = null,
        //val author: String? = null,
        @PrimaryKey val uid: Int,
        @ColumnInfo(name = "title") val title: String? = null,
        @ColumnInfo(name = "author") val author: String? = null
)