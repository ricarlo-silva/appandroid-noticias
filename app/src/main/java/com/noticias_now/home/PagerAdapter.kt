package com.noticias_now.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Created by ricarlo on 12/11/2016.
 */
class PagerAdapter(
        private val pages: Map<String, Fragment>,
        fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getPageTitle(position: Int): CharSequence {
        return pages.keys.elementAt(position)
    }

    override fun getItem(position: Int): Fragment {
        return pages.values.elementAt(position)
    }

    override fun getCount(): Int {
        return pages.size
    }
}