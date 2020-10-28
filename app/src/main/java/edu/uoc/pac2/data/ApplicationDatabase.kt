package edu.uoc.pac2.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.*

/**
 * Room Application Database
 */

@Database(entities = [Book::class], version = 4)
abstract class ApplicationDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        private var INSTANCE: ApplicationDatabase? = null

        fun getInstance(context: Context): ApplicationDatabase {

            if ( INSTANCE == null ) {
                synchronized(ApplicationDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, ApplicationDatabase::class.java, "book")
                                //.allowMainThreadQueries() // Uncomment if you don't want to use RxJava or coroutines just yet (blocks UI thread)
                                .addCallback(object : Callback() {
                                    override fun onCreate(db: SupportSQLiteDatabase) {
                                        super.onCreate(db)
                                        Log.d("MoviesDatabase", "populating with data...")
                                        //GlobalScope.launch(Dispatchers.IO) { rePopulateDb(INSTANCE) }
                                    }
                                }).build()
                    }
                }
            }
           /* if( INSTANCE == null ) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                        ApplicationDatabase::class.java, "book"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }*/
            return INSTANCE!!
        }
    }
}