package com.jumpstopstudios.swiper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

const val PAGE_COUNT = 5

/**
 * Basic pager adapter.
 * No special features.
 */
class SlidePagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
    override fun getItemCount() = PAGE_COUNT
    override fun createFragment(position: Int) = PageFragment(position)
}

const val PADDING_PAGE_COUNT = 1

class InfinitePagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
    override fun getItemCount() = PAGE_COUNT + (PADDING_PAGE_COUNT * 2)

    override fun createFragment(position: Int): Fragment {
        var adjustedPosition = when {
            position < PADDING_PAGE_COUNT -> position + PAGE_COUNT - 1
            position >= PAGE_COUNT + PADDING_PAGE_COUNT -> position - PAGE_COUNT - 1
            else -> position - 1
        }
        return PageFragment(adjustedPosition)
    }
}