package com.aidangrabe.standup

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager

/**
 *
 */
class OnFragmentChangedListener(val viewPager: ViewPager, val adapter: FragmentPagerAdapter, val callback: (Fragment) -> Unit) : ViewPager.OnPageChangeListener {

    override fun onPageScrollStateChanged(state: Int) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            val currentItem = viewPager.currentItem
            callback.invoke(adapter.getItem(currentItem))
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
    }
}