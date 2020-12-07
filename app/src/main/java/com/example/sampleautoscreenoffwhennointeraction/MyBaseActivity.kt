package com.example.sampleautoscreenoffwhennointeraction

import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

open class MyBaseActivity : AppCompatActivity() {

    companion object {
        const val DISCONNECT_TIMEOUT: Long = 5000 // 300000 -> 5 min = 5 * 60 * 1000 ms
    }

    private fun turnOnScreen() {
        // turn on screen
        val params = window.attributes
        params.screenBrightness = -1f
        window.attributes = params
    }

    private fun turnOffScreen() {
        // turn off screen
        val params = window.attributes
        params.screenBrightness = 0f
        window.attributes = params
    }

    private val disconnectHandler = Handler(Looper.getMainLooper())
    private val disconnectCallback = Runnable {
        // Perform any required operation on disconnect
        turnOffScreen()
    }

    fun resetDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback)
        disconnectHandler.postDelayed(disconnectCallback, DISCONNECT_TIMEOUT)
    }

    fun stopDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback)
    }

    override fun onUserInteraction() {
        resetDisconnectTimer()
        turnOnScreen()
    }

    public override fun onResume() {
        super.onResume()
        resetDisconnectTimer()
    }

    public override fun onStop() {
        super.onStop()
        stopDisconnectTimer()
    }

}