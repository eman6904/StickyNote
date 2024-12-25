package com.example.stickynotes.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.stickynotes.data.tables.StickyNotesTable
@Dao
public interface StickyNotesDAO {

    @Insert
    suspend fun insertNote(stickyNotesTable: StickyNotesTable)
    @Query("select * from StickyNotesTable")
    fun getNotes():LiveData<List<StickyNotesTable>>
    @Query("delete from StickyNotesTable where id in (:noteIds)")
    suspend fun deleteNote(noteIds: List<Int>)
    @Update
    suspend fun updateNote(note: StickyNotesTable)

}