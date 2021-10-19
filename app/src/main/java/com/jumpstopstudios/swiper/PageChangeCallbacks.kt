package com.jumpstopstudios.swiper

import androidx.viewpager2.widget.ViewPager2


class SleekPageChangeCallback(private val viewPager: ViewPager2) : ViewPager2.OnPageChangeCallback(){


    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        if (positionOffset <= 0.01f) {
            when {
                position < PADDING_PAGE_COUNT -> viewPager.setCurrentItem(position + PAGE_COUNT, false)
                position >= PAGE_COUNT + PADDING_PAGE_COUNT ->viewPager.setCurrentItem(position - PAGE_COUNT, false)
            }
        }
    }
}