package com.jumpstopstudios.swiper

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity(){

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.view_pager)
        if (PADDING_PAGE_COUNT > 1) viewPager.offscreenPageLimit = PADDING_PAGE_COUNT - 1

        // Basic:
        //viewPager.adapter = SlidePagerAdapter(this)

        // Infinite loop:
        viewPager.adapter = InfinitePagerAdapter(this)
        viewPager.setCurrentItem(PADDING_PAGE_COUNT, false)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (positionOffset <= 0.01f) {
                    when {
                        position < PADDING_PAGE_COUNT -> viewPager.setCurrentItem(position + PAGE_COUNT, false)
                        position >= PAGE_COUNT + PADDING_PAGE_COUNT ->viewPager.setCurrentItem(position - PAGE_COUNT, false)
                    }
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == PADDING_PAGE_COUNT) super.onBackPressed()
        //else viewPager.currentItem-- // doesn't make sense in infinite loop
        else viewPager.currentItem = PADDING_PAGE_COUNT
    }
}