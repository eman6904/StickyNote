package com.example.stickynotes.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.stickynotes.ui.fragments.FavoriteNotesFragment
import com.example.stickynotes.ui.fragments.NewStickyNoteFragment
import com.example.stickynotes.ui.fragments.StickyNotesDisplayFragment
import com.example.stickynotes.ui.fragments.ViewPagerFragment

class TabsAdapter (fragmentActivity: ViewPagerFragment) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
      return  when(position){
           0 -> NewStickyNoteFragment()
           1 -> StickyNotesDisplayFragment()
          else -> FavoriteNotesFragment()
      }
    }

}