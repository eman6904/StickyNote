package com.example.stickynotes.data.tables

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class StickyNotesTable(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val stickyNoteContent:String,
    val stickyNoteColor:String,
    val fontColor:String,
    val isFavorite:Boolean,
)
