package com.example.stickynotes.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.stickynotes.data.dao.StickyNotesDAO
import com.example.stickynotes.data.tables.StickyNotesTable

@Database(entities = [StickyNotesTable::class], version = 1, exportSchema = false)
abstract class StickyNoteDatabase : RoomDatabase()
{
    abstract fun stickyNoteDao(): StickyNotesDAO
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: StickyNoteDatabase? = null

        fun getDatabase(context: Context): StickyNoteDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StickyNoteDatabase::class.java,
                    "posts_table"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}