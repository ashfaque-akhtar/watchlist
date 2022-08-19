package com.watchlist.demoApp.ui.watchlist.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class WatchlistPagerAdapter( var activity: AppCompatActivity, var fragments: List<Fragment>) : FragmentStateAdapter(activity){

    override fun getItemCount(): Int =
        fragments.size


    override fun createFragment(position: Int): Fragment {
      return  fragments[position]
    }
}