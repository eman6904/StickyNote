package com.example.stickynotes.ui.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.HorizontalScrollView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.stickynotes.data.tables.StickyNotesTable
import com.example.stickynotes.data.viewModel.StickyNotesViewModel
import com.example.stickynotes.databinding.FragmentStickyNotesDisplayBinding
import com.example.stickynotes.ui.adapters.ColorsAdapter
import com.example.stickynotes.ui.adapters.NoteAdapter
import com.example.stickynotes.ui.models.ColorModel


class StickyNotesDisplayFragment : Fragment() {

   // private var  adapter:NoteAdapter? = null
    private val stickyNoteViewModel: StickyNotesViewModel by activityViewModels()
    private val binding: FragmentStickyNotesDisplayBinding by lazy {
        FragmentStickyNotesDisplayBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        stickyNoteViewModel.combinedLiveData.observe(viewLifecycleOwner) { values ->

            if (values != null && values.size >= 2) {
                val notes = values[0] as? List<StickyNotesTable> ?: emptyList()
                val selectionMode = values[1] as? Boolean ?: false

                val adapter = NoteAdapter(notes,stickyNoteViewModel)
                binding.stickyNotesRecycler.layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                binding.stickyNotesRecycler.adapter = adapter

                adapter.setOnClickListener(object :
                    NoteAdapter.OnClickListener {
                    override fun onClick(position: Int, model: StickyNotesTable) {

                        if(selectionMode) {

                            if(stickyNoteViewModel.selectedIds.value!!.contains(notes[position].id))
                                stickyNoteViewModel.removeFromSelectedIds(notes[position].id)
                            else
                                stickyNoteViewModel.addToSelectedIds(notes[position].id)
                            adapter.notifyDataSetChanged()
                        }

                    }

                    override fun onLongClick(position: Int, model: StickyNotesTable) {

                        stickyNoteViewModel.setSelectionMode()
                        stickyNoteViewModel.addToSelectedIds(notes[position].id)

                    }
                })
                requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

                    if(stickyNoteViewModel.selectionMode.value == true)
                    {
                        stickyNoteViewModel.removeSelectionMode()
                        adapter.notifyDataSetChanged()
                    }else
                      activity?.finish()
                }
            }

        }
        binding.deleteBtn.setOnClickListener {

            stickyNoteViewModel.deleteNotes(stickyNoteViewModel.selectedIds.value!!)
            stickyNoteViewModel.removeSelectionMode()

        }
    }

}