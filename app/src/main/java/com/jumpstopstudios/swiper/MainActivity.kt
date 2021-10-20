package com.jumpstopstudios.swiper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        val pageChangeCallback = SleekPageChangeCallback(viewPager)
        viewPager.registerOnPageChangeCallback(pageChangeCallback)

        // Show preview of page on either side:
        // Adapted from https://stackoverflow.com/a/58088398
        // Also requires dimensions and horizontal margin item decoration.
        viewPager.setPageTransformer(SleekPageTransformer(pageChangeCallback))
        val itemDecoration = PageWidthItemDecoration()
        viewPager.addItemDecoration(itemDecoration)
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == PADDING_PAGE_COUNT) super.onBackPressed()
        //else viewPager.currentItem-- // doesn't make sense in infinite loop
        else viewPager.currentItem = PADDING_PAGE_COUNT
    }
}