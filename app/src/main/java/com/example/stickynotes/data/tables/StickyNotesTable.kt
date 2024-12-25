package com.example.stickynotes.data.tables

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class StickyNotesTable(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    var stickyNoteContent:String,
    var stickyNoteColor:String,
    var fontColor:String,
    var lockPassword:String = "",
    var isFavorite:Boolean = false,
    var isLocked:Boolean = false
)
