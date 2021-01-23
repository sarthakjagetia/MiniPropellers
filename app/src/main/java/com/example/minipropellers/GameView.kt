package com.example.minipropellers

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    private val thread = MainThread(holder, this)

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
            canvas.drawColor(Color.WHITE)
            val paint = Paint()
            paint.color = Color.rgb(250, 0, 0)
            canvas.drawRect(100F, 100F, 200F, 200F, paint)
        }
    }

    fun update() {

    }
}