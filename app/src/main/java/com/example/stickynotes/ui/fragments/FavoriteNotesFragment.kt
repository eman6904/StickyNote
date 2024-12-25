package com.example.stickynotes.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.stickynotes.data.viewModel.StickyNotesViewModel
import com.example.stickynotes.databinding.FragmentFavoriteNotesBinding
import com.example.stickynotes.ui.adapters.NoteAdapter

class FavoriteNotesFragment : Fragment() {
    private lateinit var navController: NavController
    private val stickyNoteViewModel: StickyNotesViewModel by activityViewModels()
    private val binding: FragmentFavoriteNotesBinding by lazy {
        FragmentFavoriteNotesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stickyNoteViewModel.notes.observe(viewLifecycleOwner){notes->

            val favoriteNotes = notes.filter {
                it.isFavorite == true
            }
            val adapter = NoteAdapter(favoriteNotes,stickyNoteViewModel)
            binding.favoriteNoteList.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            binding.favoriteNoteList.adapter = adapter
        }
    }
}