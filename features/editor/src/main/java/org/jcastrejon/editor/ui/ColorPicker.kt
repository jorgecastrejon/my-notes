package org.jcastrejon.editor.ui

import android.graphics.Color
import kotlin.random.Random

object ColorPicker {

    private const val UNTIL = 256
    private const val HALF = 2

    fun random(): Int {
        val random = Random(System.currentTimeMillis())
        val baseColor = Color.WHITE

        val baseRed = Color.red(baseColor)
        val baseGreen = Color.green(baseColor)
        val baseBlue = Color.blue(baseColor)

        val red = (baseRed + random.nextInt(UNTIL)) / HALF
        val green = (baseGreen + random.nextInt(UNTIL)) / HALF
        val blue = (baseBlue + random.nextInt(UNTIL)) / HALF

        return Color.rgb(red, green, blue)
    }

}