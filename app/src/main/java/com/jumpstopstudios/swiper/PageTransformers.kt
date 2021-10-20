package com.jumpstopstudios.swiper

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

/**
 * SleekPageTransformer makes it easy to customize how pages transform as they
 * move to and from the centre of the ViewPager2.
 * Use in tandem with PageWidthItemDecoration to see the off-centre pages on either side.
 *
 * @param pageChangeCallback
 * The callback for getting the current swipe direction and swipe progress. Used for animating
 * the page during swipes.
 *
 * @param pageTranslationFactorX
 * How much pages are translated horizontally towards the centre, relative to the original page
 * width and horizontal distance from the centre.
 * When between 0.0 and 1.0, closer to 1.0 means the pages are closer together.
 * E.g.
 * 0.5 -> off-centre pages are half their original width closer to the centre.
 * 2.0 -> off-centre pages are in reverse order (they've been translated past the centre
 * to the opposite side).
 * 0.0 -> no translation (like normal ViewPager2).
 *
 * @param pageTranslationFactorY
 * How much pages are translated vertically away from the centre, relative to the original page
 * height and horizontal distance from the centre.
 * When between -1.0 and 1.0, closer to -1.0 means the following pages are further down, and
 * closer to 1.0 means the following pages are further up.
 * Note: use with parameter "pageTranslationYSymmetrical" to change how pages are affected by
 * this.
 * E.g.
 * 0.5 -> off-centre pages are half their original height higher than the centre page.
 * -0.5 -> off-centre pages are half their original height lower than the centre page.
 * 0.0 -> no translation (like normal ViewPager2).
 *
 * @param pageTranslationYSymmetrical
 * Determines the shape produced by the Y translation.
 * True -> all off-centre pages are translated in the same direction, making a V shape
 * (or upside-down V if "pageTranslationFactorY" is negative).
 * False -> off-centre pages on each side are translated in opposite directions, making a / shape
 * (or \ if "pageTranslationFactorY" is negative).
 *
 * @param pageScaleFactor
 * How pages are scaled, relative to the original page size and distance from the centre.
 * When between 0.0 and 1.0, closer to 0.0 means the off-centre pages are smaller.
 * Note: off-centre page size is bounded between original size and pageScaleFactor * original size.
 * E.g.
 * 0.5 -> off-centre pages shrink to half the size.
 * 2.0 -> off-centre pages grow to twice the size.
 * 1.0 -> no scaling (like normal ViewPager2).
 *
 * @param pageAlphaFactor
 * The alpha value of off-centre pages. Must be from 0.0 to 1.0.
 * Closer to 0.0 means pages fade out more, and closer to 1.0 means they fade out less.
 * E.g.
 * 0.5 -> off-centre pages fade out to half the opacity.
 * 0.9 -> off-centre pages fade out very slightly.
 * 1.0 -> full opacity (like normal ViewPager2).
 */
class SleekPageTransformer(
    private val pageChangeCallback: SleekPageChangeCallback,
    private val pageTranslationFactorX: Double = 0.5,
    private val pageTranslationFactorY: Double = 0.15,
    private val pageTranslationYSymmetrical: Boolean = true,
    private val pageScaleFactor: Double = 0.75,
    private val pageAlphaFactor: Double = 0.5
    ) : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float){
        val absPosition = abs(position)

        // Translate off-centre pages:
        // Pages further from the centre are translated more towards the centre.
        page.translationX = (page.width * pageTranslationFactorX * -position).toFloat()
        if (pageTranslationYSymmetrical) page.translationY = (page.height * pageTranslationFactorY * -absPosition).toFloat() // bent diagonal
        else page.translationY = (page.height * pageTranslationFactorY * -position).toFloat() // full diagonal, cool effect

        // Scale off-centre pages:
        // Pages further from the centre are scaled more (with a limit of the offscreenPageScaleFactor).
        var scale = when {
            absPosition > 1 -> pageScaleFactor
            position < 0 -> (1 - pageScaleFactor) * position + 1
            else -> (pageScaleFactor - 1) * position + 1
        }
        /*var scale = when {
            abs(position) > 1 -> pageScaleFactor
            else -> abs(pageScaleFactor - 1) * position + 1 // cool effect, look into further
        }*/
        val scaleFloat = scale.toFloat()
        page.scaleX = scaleFloat
        page.scaleY = scaleFloat

        // Fade off-centre pages:
        page.alpha = when {
            absPosition > 1 -> pageAlphaFactor.toFloat()
            else -> ((pageAlphaFactor - 1) * absPosition + 1).toFloat()
        }

        // Rotation animation:
        // Adapted from https://www.bornfight.com/blog/how-to-build-animated-lists-with-motionlayout-and-viewpager2/
        if (pageChangeCallback.goingLeft) ((page as ViewGroup).getChildAt(0) as MotionLayout).setTransition(R.id.leftToRight)
        else ((page as ViewGroup).getChildAt(0) as MotionLayout).setTransition(R.id.rightToLeft)
        (page.getChildAt(0) as MotionLayout).progress = pageChangeCallback.progress
    }
}