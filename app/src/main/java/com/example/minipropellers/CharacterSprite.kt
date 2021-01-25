package com.example.minipropellers

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas

class CharacterSprite(private val bitmap: Bitmap) {
    private var x: Float = 100f
    var y: Float = 100f
    private var xVelocity = 10
    var yVelocity = 5
    private val screenWidth: Int = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight: Int = Resources.getSystem().displayMetrics.heightPixels

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, x, y, null)
    }

    fun update() {
        x += xVelocity;
        y += yVelocity;
        if ((x > screenWidth - bitmap.width) || (x < 0)) {
            xVelocity *= -1
        }
        if ((y > screenHeight - bitmap.height) || (y < 0)) {
            yVelocity *= -1
        }
    }
}