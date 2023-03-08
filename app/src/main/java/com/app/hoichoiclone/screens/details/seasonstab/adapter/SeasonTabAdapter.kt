package com.app.hoichoiclone.screens.details.seasonstab.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.hoichoiclone.screens.details.seasonstab.SeasonListFragment

class SeasonTabAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    private var tabCount = 0
    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {
        val fragment = SeasonListFragment()
        fragment.arguments = Bundle().apply {
            putInt(SeasonListFragment.ARG_POSITION, position + 1)
        }
        return fragment
    }

    fun setTabCount(count: Int) {
        tabCount = count
    }
}
