package com.jumpstopstudios.swiper

import androidx.viewpager2.widget.ViewPager2
import kotlin.properties.Delegates


class SleekPageChangeCallback(private val viewPager: ViewPager2) : ViewPager2.OnPageChangeCallback(){

    private var prevOffset = 0.0f
    var goingLeft: Boolean by Delegates.notNull()
    var progress: Float by Delegates.notNull()

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels)

        // When a fake/copy page reaches the centre, seamlessly switch to the real page.
        // (For infinite looping)
        if (positionOffset <= 0.01f) {
            when {
                position < PADDING_PAGE_COUNT -> viewPager.setCurrentItem(position + PAGE_COUNT, false)
                position >= PAGE_COUNT + PADDING_PAGE_COUNT ->viewPager.setCurrentItem(position - PAGE_COUNT, false)
            }
        }

        // Determine swipe direction and progress.
        // (For rotation animation)
        goingLeft = prevOffset > positionOffset
        prevOffset = positionOffset
        progress = when (position) {
            position -> positionOffset
            position + 1 -> 1 - positionOffset
            position - 1 -> 1 - positionOffset
            else -> 0f
        }
    }
}