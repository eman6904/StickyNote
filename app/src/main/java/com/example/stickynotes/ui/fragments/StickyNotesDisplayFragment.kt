package com.example.stickynotes.ui.fragments

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Adapter
import android.widget.HorizontalScrollView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.stickynotes.R
import com.example.stickynotes.data.tables.StickyNotesTable
import com.example.stickynotes.data.viewModel.StickyNotesViewModel
import com.example.stickynotes.databinding.FragmentStickyNotesDisplayBinding
import com.example.stickynotes.ui.adapters.ColorsAdapter
import com.example.stickynotes.ui.adapters.NoteAdapter
import com.example.stickynotes.ui.models.ColorModel


class StickyNotesDisplayFragment : Fragment() {

    private lateinit var navController: NavController
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

        navController = (parentFragment as? ViewPagerFragment)!!.findNavController()
        stickyNoteViewModel.combinedLiveData.observe(viewLifecycleOwner) { values ->

            if (values != null && values.size >= 3) {
                var notes = (values[0] as? List<StickyNotesTable> ?: emptyList())
                var selectionMode = values[1] as? Boolean ?: false
                val searchedQuery = values[2] as?String?:""
                notes = notes.filter {
                    it.stickyNoteContent.contains(searchedQuery,true)
                }
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
                        }else{
                            stickyNoteViewModel.setSelectedNote(notes[position])
                            navController.navigate(R.id.action_viewPagerFragment_to_noteDetailsFrgment)
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
        stickyNoteViewModel.selectedIds.observe(viewLifecycleOwner){list->

            if(list.isEmpty())
                binding.deleteBtn.visibility = View.GONE
            else
                binding.deleteBtn.visibility = View.VISIBLE
        }
        binding.deleteBtn.setOnClickListener {

            stickyNoteViewModel.deleteNotes(stickyNoteViewModel.selectedIds.value!!)
            stickyNoteViewModel.removeSelectionMode()

        }
        binding.searchBtn.setOnClickListener {

            binding.searchField.visibility = View.VISIBLE

        }
        binding.searchField.setStartIconOnClickListener {

            binding.searchField.visibility = View.GONE
        }
        binding.textQuery.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {

                stickyNoteViewModel.setSearchedQuery(text.toString())

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

}

