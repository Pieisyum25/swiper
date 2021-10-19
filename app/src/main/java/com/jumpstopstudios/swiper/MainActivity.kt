package com.jumpstopstudios.swiper

import android.os.Bundle
import android.util.Log
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

        // Show preview of page on either side:
        // Adapted from https://stackoverflow.com/a/58088398
        // Also requires dimensions and horizontal margin item decoration.

        var pageTranslationX: Float
        with (resources){
            pageTranslationX = getDimension(R.dimen.viewpager_next_item_visible_amount)
            + getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        }
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            val scale = 1 - (0.0008f * pageTranslationX * kotlin.math.abs(position))
            page.scaleX = scale
            page.scaleY = scale
        }
        viewPager.setPageTransformer(pageTransformer)
        val itemDecoration = HorizontalMarginItemDecoration(this, R.dimen.viewpager_current_item_horizontal_margin)
        viewPager.addItemDecoration(itemDecoration)
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == PADDING_PAGE_COUNT) super.onBackPressed()
        //else viewPager.currentItem-- // doesn't make sense in infinite loop
        else viewPager.currentItem = PADDING_PAGE_COUNT
    }
}