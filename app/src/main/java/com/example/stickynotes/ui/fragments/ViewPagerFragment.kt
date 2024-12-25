package com.example.stickynotes.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.viewpager2.widget.ViewPager2
import com.example.stickynotes.R
import com.example.stickynotes.data.viewModel.StickyNotesViewModel
import com.example.stickynotes.databinding.FragmentViewPagerBinding
import com.example.stickynotes.ui.adapters.TabsAdapter
import com.google.android.material.tabs.TabLayout


class ViewPagerFragment : Fragment() {

    private val stickyNoteViewModel: StickyNotesViewModel by activityViewModels()
    private val binding: FragmentViewPagerBinding by lazy {
        FragmentViewPagerBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.isSaveEnabled = false
        setTabs(
            binding = binding,
            fragment = this,
            context = requireContext()
        )
    }


    private fun setTabs(
        binding: FragmentViewPagerBinding,
        fragment: ViewPagerFragment,
        context: Context
    ) {

        stickyNoteViewModel.selectedTab.observe(viewLifecycleOwner) { position ->

            binding.viewPager.setCurrentItem(position, false)
            binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
        }

        binding.viewPager.adapter = TabsAdapter(fragment)
        if(binding.tabLayout.tabCount<3){
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Create"))
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Notes"))
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Favorite"))
        }

       when(stickyNoteViewModel.selectedTab.value){
           0->{
               binding.tabLayout.getTabAt(stickyNoteViewModel.selectedTab.value!!)!!.view.setBackground(
                   ContextCompat.getDrawable(
                       fragment.requireContext(),
                       R.drawable.selected_tab_shape
                   )
               )
               binding.tabLayout.getTabAt(stickyNoteViewModel.selectedTab.value!!)!!.setIcon(R.drawable.write_note_icon)
                   .icon?.setTint(ContextCompat.getColor(context, R.color.appColor2))

               binding.tabLayout.getTabAt(1)!!.setIcon(R.drawable.notes_icon)
                   .icon?.setTint(ContextCompat.getColor(context, R.color.white))
               binding.tabLayout.getTabAt(2)!!.setIcon(R.drawable.favorite_icon)
                   .icon?.setTint(ContextCompat.getColor(context, R.color.white))
           }
           1->{
               binding.tabLayout.getTabAt(stickyNoteViewModel.selectedTab.value!!)!!.view.setBackground(
                   ContextCompat.getDrawable(
                       fragment.requireContext(),
                       R.drawable.selected_tab_shape
                   )
               )
               binding.tabLayout.getTabAt(stickyNoteViewModel.selectedTab.value!!)!!.setIcon(R.drawable.notes_icon)
                   .icon?.setTint(ContextCompat.getColor(context, R.color.appColor2))

               binding.tabLayout.getTabAt(0)!!.setIcon(R.drawable.write_note_icon)
                   .icon?.setTint(ContextCompat.getColor(context, R.color.white))
               binding.tabLayout.getTabAt(2)!!.setIcon(R.drawable.favorite_icon)
                   .icon?.setTint(ContextCompat.getColor(context, R.color.white))
           }
           2->{
               binding.tabLayout.getTabAt(stickyNoteViewModel.selectedTab.value!!)!!.view.setBackground(
                   ContextCompat.getDrawable(
                       fragment.requireContext(),
                       R.drawable.selected_tab_shape
                   )
               )
               binding.tabLayout.getTabAt(stickyNoteViewModel.selectedTab.value!!)!!.setIcon(R.drawable.favorite_icon)
                   .icon?.setTint(ContextCompat.getColor(context, R.color.appColor2))

               binding.tabLayout.getTabAt(1)!!.setIcon(R.drawable.notes_icon)
                   .icon?.setTint(ContextCompat.getColor(context, R.color.white))
               binding.tabLayout.getTabAt(0)!!.setIcon(R.drawable.write_note_icon)
                   .icon?.setTint(ContextCompat.getColor(context, R.color.white))
           }
       }


        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.icon?.setTint(ContextCompat.getColor(context, R.color.appColor2))
                if (tab != null) {
                    stickyNoteViewModel.updateSelectedTab(tab.position)
                    tab.view.setBackground(
                        ContextCompat.getDrawable(
                            fragment.requireContext(),
                            R.drawable.selected_tab_shape
                        )
                    )
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.icon?.setTint(ContextCompat.getColor(context, R.color.white))
                if (tab != null) {
                    tab.view.setBackground(
                        ContextCompat.getDrawable(
                            fragment.requireContext(),
                            R.drawable.tab_indicator_shape
                        )
                    )
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                stickyNoteViewModel.updateSelectedTab(position)

            }
        })
    }
}
