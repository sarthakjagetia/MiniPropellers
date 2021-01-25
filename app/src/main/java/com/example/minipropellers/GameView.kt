package com.example.minipropellers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    private val thread = MainThread(holder, this)
    private val characterSprite = CharacterSprite(BitmapFactory.decodeResource(resources, R.drawable.drone))
    private val backgroundBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.background_night)
    private val backgroundPaint = Paint()

    init {
        holder.addCallback(this)
        isFocusable = true
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        thread.running = true
        thread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        var retry = true
        while (retry) {
            try {
                thread.running = false
                thread.join()
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            retry = false
        }
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas != null) {
            canvas.drawRGB(0, 100, 205);
//            val dest = Rect(0, 0, width, height)
//            backgroundPaint.isFilterBitmap = true
//            canvas.drawBitmap(backgroundBitmap, null, dest, backgroundPaint)
            characterSprite.draw(canvas);
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        characterSprite.y = characterSprite.y - (characterSprite.yVelocity * 50)
        return super.onTouchEvent(event)
    }

    fun update() {
        characterSprite.update()
    }
}