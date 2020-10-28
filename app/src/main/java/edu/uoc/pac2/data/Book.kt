package edu.uoc.pac2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * A book Model representing a piece of content.
 */

@Entity(tableName = "book")
data class Book(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "uid") val uid: Int, //clave primaria, autoincremental
        @ColumnInfo(name = "author") val author: String? = null,
        @ColumnInfo(name = "description") val description: String? = null,
        @ColumnInfo(name = "publicationDate") val publicationDate: String? = null,
        @ColumnInfo(name = "title") val title: String? = null,
        @ColumnInfo(name = "urlImage") val urlImage: String? = null
)