package com.example.viewpandrecycler

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.viewpandrecycler.ui.BlankFragment
import com.example.viewpandrecycler.ui.BlankFragment2
import com.example.viewpandrecycler.ui.BlankFragment3

class VpAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{BlankFragment()}
            1->{BlankFragment2()}
            2->{BlankFragment3()}
            else->{BlankFragment()}
        }
    }
}