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
    override fun createFragment(position: Int) = PageFragment.newInstance(position)
}

/**
 * Number of fake pages on each end (for infinite looping).
 * Needs to be at least 2 to work with side previews.
 */
const val PADDING_PAGE_COUNT = 2

/**
 * Infinite loop pager adapter.
 * Scrolling appears endless.
 */
class InfinitePagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
    override fun getItemCount() = PAGE_COUNT + (PADDING_PAGE_COUNT * 2)

    override fun createFragment(position: Int): Fragment {
        var adjustedPosition = position
        when {
            position < PADDING_PAGE_COUNT -> adjustedPosition += PAGE_COUNT
            position >= PAGE_COUNT + PADDING_PAGE_COUNT -> adjustedPosition -= PAGE_COUNT
        }
        adjustedPosition -= PADDING_PAGE_COUNT
        return PageFragment.newInstance(adjustedPosition)
    }
}