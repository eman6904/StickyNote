package com.example.stickynotes.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.viewpager2.widget.ViewPager2
import com.example.stickynotes.R
import com.example.stickynotes.databinding.FragmentViewPagerBinding
import com.example.stickynotes.ui.adapters.TabsAdapter
import com.google.android.material.tabs.TabLayout


class ViewPagerFragment : Fragment() {
    private lateinit var navController: NavController
    private val binding: FragmentViewPagerBinding by lazy {
        FragmentViewPagerBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      setTabs(
          binding = binding,
          fragment = this,
          context = requireContext()
      )
    }

}

private fun setTabs(
    binding: FragmentViewPagerBinding,
    fragment: ViewPagerFragment,
    context: Context
) {


    binding.viewPager.adapter = TabsAdapter(fragment)
    binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Create"))
    binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Notes"))
    binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Favorite"))

    binding.tabLayout.getTabAt(0)!!.view.setBackground(
            ContextCompat.getDrawable(
                fragment.requireContext(),
                R.drawable.selected_tab_shape
            ))
         binding.tabLayout.getTabAt(0)!!.setIcon(R.drawable.write_note_icon)
        .icon?.setTint(ContextCompat.getColor(context, R.color.white))

    binding.tabLayout.getTabAt(1)!!.setIcon(R.drawable.notes_icon)
        .icon?.setTint(ContextCompat.getColor(context, R.color.white))
    binding.tabLayout.getTabAt(2)!!.setIcon(R.drawable.favorite_icon)
        .icon?.setTint(ContextCompat.getColor(context, R.color.white))


    binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.icon?.setTint(ContextCompat.getColor(context, R.color.white))
            if (tab != null) {
                binding.viewPager.currentItem = tab.position
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

            binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
        }
    })
}
