package <%= appPackage %>.tool.viewpager.animation

import android.support.v4.view.ViewPager
import android.view.View

/**
 * Created by Kristal on 5/22/2017.
 */

class ZoomOutPageTransformer(
        private val minScale: Float = DEFAULT_MIN_SCALE,
        private val minAlpha: Float = DEFAULT_MIN_ALPHA
) : ViewPager.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width
        val pageHeight = view.height

        if (position < -1 || position > 1) {
            view.alpha = 0f
        } else {
            val scaleFactor = Math.max(minScale, 1 - Math.abs(position))
            val vertMargin = pageHeight * (1 - scaleFactor) / 2
            val horzMargin = pageWidth * (1 - scaleFactor) / 2
            if (position < 0) {
                view.translationX = horzMargin - vertMargin / 2
            } else {
                view.translationX = -horzMargin + vertMargin / 2
            }

            view.scaleX = scaleFactor
            view.scaleY = scaleFactor

            view.alpha = minAlpha + (scaleFactor - minScale) / (1 - minScale) * (1 - minAlpha)
        }
    }

    companion object {
        val DEFAULT_MIN_SCALE = 0.85f
        val DEFAULT_MIN_ALPHA = 0.5f
    }
}
