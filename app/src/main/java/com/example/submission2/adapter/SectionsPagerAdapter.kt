package com.example.submission2.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submission2.FollowersFragment
import com.example.submission2.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val userId:String) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment.userName(userId)
            1 -> fragment = FollowingFragment.userName(userId)
        }
        return fragment as Fragment
    }
}