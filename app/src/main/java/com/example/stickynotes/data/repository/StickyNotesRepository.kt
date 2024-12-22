package com.example.stickynotes.data.repository

import androidx.lifecycle.LiveData
import com.example.stickynotes.data.dao.StickyNotesDAO
import com.example.stickynotes.data.tables.StickyNotesTable

class StickyNotesRepository(private val stickyNotesDao: StickyNotesDAO) {


    val notes: LiveData<List<StickyNotesTable>> = stickyNotesDao.getNotes()

    suspend fun insert(stickyNotesTable: StickyNotesTable) {
        stickyNotesDao.insertNote(stickyNotesTable)
    }

    suspend fun deleteNotes(noteIds: List<Int>) {
        stickyNotesDao.deleteNote(noteIds)
    }
}
