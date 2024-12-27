package com.example.stickynotes.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.stickynotes.data.database.StickyNoteDatabase
import com.example.stickynotes.data.repository.StickyNotesRepository
import com.example.stickynotes.data.tables.StickyNotesTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StickyNotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StickyNotesRepository

    val notes: LiveData<List<StickyNotesTable>> get() = repository.notes

    private val _stickyNoteContent = MutableLiveData<String>("")
    val stickyNoteContent: LiveData<String> get() = _stickyNoteContent

    private val _selectedIds = MutableLiveData<ArrayList<Int>>()
    val selectedIds: LiveData<ArrayList<Int>> get() = _selectedIds

    private val _stickyNoteColor = MutableLiveData("#F0BB78")
    val stickyNoteColor: LiveData<String> get() = _stickyNoteColor

    private val _FontColor = MutableLiveData("#FFFFFFFF")
    val fontColor: LiveData<String> get() = _FontColor

    private val _searchedQuery = MutableLiveData("")
    val searchedQuery: LiveData<String> get() = _searchedQuery

    private val _selectionMode = MutableLiveData(false)
    val selectionMode: LiveData<Boolean> get() = _selectionMode

    val combinedLiveData = MediatorLiveData<List<Any>>()

    private val _selectedTab = MutableLiveData(0)
    val selectedTab: LiveData<Int> get() = _selectedTab

    private val _selectedNote = MutableLiveData<StickyNotesTable>()
    val selectedNote: LiveData<StickyNotesTable> get() = _selectedNote

    init {
        val dao = StickyNoteDatabase.getDatabase(application).stickyNoteDao()
        repository = StickyNotesRepository(dao)
        _selectedIds.value = ArrayList()
        combinedLiveData.addSource(notes) { notes ->
            combinedLiveData.value = listOf(notes, selectionMode.value,searchedQuery.value).filterNotNull()
        }
        combinedLiveData.addSource(selectionMode) { selectionMode ->
            combinedLiveData.value = listOf(notes.value, selectionMode,searchedQuery.value).filterNotNull()
        }
        combinedLiveData.addSource(searchedQuery) { searchedQuery ->
            combinedLiveData.value = listOf(notes.value, selectionMode.value,searchedQuery).filterNotNull()
        }
    }


    fun insertNote(note: StickyNotesTable) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNote(note)
    }

    fun deleteNotes(noteIds: List<Int>) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNotes(noteIds)
    }

    fun updateNote(note: StickyNotesTable) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNote(note)
    }
    fun setStickyNoteColor(color:String){

        _stickyNoteColor.value = color
    }
    fun setFontColor(color:String){

        _FontColor.value = color
    }
    fun setStickyNoteContent(note:String){

        _stickyNoteContent.value = note
    }
    fun setSelectionMode(){

        _selectionMode.value = true
    }
    fun removeSelectionMode(){

        _selectionMode.value = false
        _selectedIds.value = ArrayList()
    }
    fun addToSelectedIds(id:Int){

        if(!_selectedIds.value!!.contains(id)) {
            val list = _selectedIds.value
            list!!.add(id)
            _selectedIds.value = list!!
        }

    }
    fun removeFromSelectedIds(id:Int){

        if(_selectedIds.value!!.contains(id)) {
            val list = _selectedIds.value
            list!!.remove(id)
            _selectedIds.value = list!!
            if(_selectedIds.value!!.isEmpty())
                removeSelectionMode()
        }
    }
    fun updateSelectedTab(position: Int) {
        _selectedTab.value = position
    }
    fun setSelectedNote(selectedNote:StickyNotesTable){

        _selectedNote.value = selectedNote
    }
    fun setSearchedQuery(searchedQuery:String){

        _searchedQuery.value = searchedQuery
    }
    fun cleanStickyNote(){

        _FontColor.value = "#FFFFFFFF"
        _stickyNoteColor.value = "#F0BB78"
    }
}
