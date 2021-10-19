package com.jumpstopstudios.swiper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

const val PAGE_COUNT = 5

class MainActivity : AppCompatActivity(){

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.view_pager)
        viewPager.offscreenPageLimit = 3
        viewPager.adapter = SlidePagerAdapter(this)
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) super.onBackPressed()
        else viewPager.currentItem--
    }

    private inner class SlidePagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
        override fun getItemCount() = PAGE_COUNT

        override fun createFragment(position: Int) = PageFragment(position)

    }
}