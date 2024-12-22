package com.example.stickynotes.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import com.example.stickynotes.databinding.FragmentFavoriteNotesBinding

class FavoriteNotesFragment : Fragment() {
    private lateinit var navController: NavController
    private val binding: FragmentFavoriteNotesBinding by lazy {
        FragmentFavoriteNotesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}