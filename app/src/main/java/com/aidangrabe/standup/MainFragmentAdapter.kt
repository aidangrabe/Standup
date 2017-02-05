package com.aidangrabe.standup

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.aidangrabe.standup.blockers.BlockersListFragment
import com.aidangrabe.standup.today.TodayListFragment
import com.aidangrabe.standup.yesterday.YesterdayListFragment

/**
 *
 */
class MainFragmentAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    val fragments: List<Fragment> = listOf(
            YesterdayListFragment(),
            TodayListFragment(),
            BlockersListFragment()
    )

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

}