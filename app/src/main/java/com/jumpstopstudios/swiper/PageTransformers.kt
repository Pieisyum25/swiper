package com.jumpstopstudios.swiper

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class SleekPageTransformer(private val pageTranslationX: Float) : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float){
        // Translate the off-centre pages towards the middle so they show on screen:
        page.translationX = -pageTranslationX * position

        // Shrink off-centre pages:
        val scale = 1 - (0.0008f * pageTranslationX * kotlin.math.abs(position))
        page.scaleX = scale
        page.scaleY = scale

        // Fade off-centre pages:
        page.alpha = 0.5f + (1 - kotlin.math.abs(position))
    }
}