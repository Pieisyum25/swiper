package com.jumpstopstudios.swiper

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.viewpager2.widget.ViewPager2

class SleekPageTransformer(
    private val pageTranslationX: Float,
    private val pageChangeCallback: SleekPageChangeCallback
    ) : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float){
        // Translate the off-centre pages towards the middle so they show on screen:
        page.translationX = -pageTranslationX * position

        // Shrink off-centre pages:
        val scale = 1 - (0.0008f * pageTranslationX * kotlin.math.abs(position))
        page.scaleX = scale
        page.scaleY = scale

        // Fade off-centre pages:
        page.alpha = 0.5f + (1 - kotlin.math.abs(position))

        // Rotation animation:
        // Adapted from https://www.bornfight.com/blog/how-to-build-animated-lists-with-motionlayout-and-viewpager2/
        if (pageChangeCallback.goingLeft) ((page as ViewGroup).getChildAt(0) as MotionLayout).setTransition(R.id.leftToRight)
        else ((page as ViewGroup).getChildAt(0) as MotionLayout).setTransition(R.id.rightToLeft)
        (page.getChildAt(0) as MotionLayout).progress = pageChangeCallback.progress
    }
}