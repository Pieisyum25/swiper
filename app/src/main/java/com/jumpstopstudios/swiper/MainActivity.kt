package com.jumpstopstudios.swiper

import android.os.Bundle
import android.view.View
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
        viewPager.registerOnPageChangeCallback(SleekPageChangeCallback(viewPager))

        // Show preview of page on either side:
        // Adapted from https://stackoverflow.com/a/58088398
        // Also requires dimensions and horizontal margin item decoration.

        var pageTranslationX: Float
        with (resources){
            pageTranslationX = getDimension(R.dimen.viewpager_next_item_visible_amount)
            + getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        }
        viewPager.setPageTransformer(SleekPageTransformer(pageTranslationX))
        val itemDecoration = HorizontalMarginItemDecoration(this, R.dimen.viewpager_current_item_horizontal_margin)
        viewPager.addItemDecoration(itemDecoration)
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == PADDING_PAGE_COUNT) super.onBackPressed()
        //else viewPager.currentItem-- // doesn't make sense in infinite loop
        else viewPager.currentItem = PADDING_PAGE_COUNT
    }
}