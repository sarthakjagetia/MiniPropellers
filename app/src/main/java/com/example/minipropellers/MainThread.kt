package com.example.minipropellers

import android.graphics.Canvas
import android.view.SurfaceHolder

class MainThread(private val surfaceHolder: SurfaceHolder,
                 private val gameView: GameView) : Thread() {
    var running = false
    var canvas: Canvas? = null
    private val targetFPS = 60
    private var startTime: Long = System.nanoTime()
    private var timeMillis = (System.nanoTime() - startTime) / 1_000_000
    private val targetTime = (1000 / targetFPS).toLong()
    private var waitTime = targetTime - timeMillis
    private var totalTime: Long = 0
    private var frameCount = 0
    private var averageFPS: Long = 0

    override fun run() {
        while (running) {
            startTime = System.nanoTime();
            canvas = null
            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    gameView.update()
                    gameView.draw(canvas)
                }
            } catch (e: Exception) {
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1_000_000
            waitTime = targetTime - timeMillis
            try {
                sleep(waitTime) //limits to 60 FPS is current FPS is > [targetFPS]
            } catch (e: Exception) {
            }
            totalTime += System.nanoTime() - startTime
            frameCount++
            if (frameCount == targetFPS) {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000)
                frameCount = 0
                totalTime = 0
                println(averageFPS)
            }
        }
    }

}