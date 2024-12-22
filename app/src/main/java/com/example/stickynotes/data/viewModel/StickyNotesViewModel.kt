package com.example.stickynotes.data.viewModel

import android.app.Application
import android.util.Log
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

    private val _stickyNoteContent = MutableLiveData<String>()
    val stickyNoteContent: LiveData<String> get() = _stickyNoteContent

    private val _selectedIds = MutableLiveData<ArrayList<Int>>()
    val selectedIds: LiveData<ArrayList<Int>> get() = _selectedIds

    private val _stickyNoteColor = MutableLiveData<String>()
    val stickyNoteColor: LiveData<String> get() = _stickyNoteColor

    private val _FontColor = MutableLiveData<String>()
    val fontColor: LiveData<String> get() = _FontColor

    private val _selectionMode = MutableLiveData<Boolean>(false)
    val selectionMode: LiveData<Boolean> get() = _selectionMode

    val combinedLiveData = MediatorLiveData<List<Any>>()

    init {
        val dao = StickyNoteDatabase.getDatabase(application).stickyNoteDao()
        repository = StickyNotesRepository(dao)
        _selectedIds.value = ArrayList()
        combinedLiveData.addSource(notes) { notes ->
            combinedLiveData.value = listOf(notes, selectionMode.value).filterNotNull()
        }
        combinedLiveData.addSource(selectionMode) { selectionMode ->
            combinedLiveData.value = listOf(notes.value, selectionMode).filterNotNull()
        }
//        combinedLiveData.addSource(liveData3) { value3 ->
//            combinedLiveData.value = listOf(liveData1.value, liveData2.value, value3, liveData4.value).filterNotNull()
//        }
//        combinedLiveData.addSource(liveData4) { value4 ->
//            combinedLiveData.value = listOf(liveData1.value, liveData2.value, liveData3.value, value4).filterNotNull()
//        }
    }


    fun insert(note: StickyNotesTable) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    fun deleteNotes(noteIds: List<Int>) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNotes(noteIds)
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

        if(!_selectedIds.value!!.contains(id))
            _selectedIds.value!!.add(id)

    }
    fun removeFromSelectedIds(id:Int){

        if(_selectedIds.value!!.contains(id))
            _selectedIds.value!!.remove(id)
    }

}
