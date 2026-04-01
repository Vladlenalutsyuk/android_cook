package com.example.myapplication3

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat

class CookingTimerService : Service() {

    private val binder = LocalBinder()
    private val handler = Handler(Looper.getMainLooper())

    private var timeLeftInSeconds = 0
    private var isRunning = false

    inner class LocalBinder : Binder() {
        fun getService(): CookingTimerService = this@CookingTimerService
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val seconds = intent?.getIntExtra("time_seconds", 0) ?: 0

        if (seconds > 0 && !isRunning) {
            timeLeftInSeconds = seconds
            startForeground(1, createNotification())
            startTimer()
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    fun getTimeLeft(): Int {
        return timeLeftInSeconds
    }

    fun isTimerRunning(): Boolean {
        return isRunning
    }

    fun stopTimer() {
        isRunning = false
        handler.removeCallbacksAndMessages(null)
        stopForeground(true)
        stopSelf()
    }

    private fun startTimer() {
        isRunning = true

        handler.post(object : Runnable {
            override fun run() {
                if (isRunning && timeLeftInSeconds > 0) {
                    timeLeftInSeconds--
                    updateNotification()
                    handler.postDelayed(this, 1000)
                } else {
                    isRunning = false
                    stopForeground(true)
                    stopSelf()
                }
            }
        })
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, "cooking_timer_channel")
            .setContentTitle(getString(R.string.timer_notification_title))
            .setContentText(getString(R.string.timer_notification_text, timeLeftInSeconds))
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setOngoing(true)
            .build()
    }

    @SuppressLint("MissingPermission")
    private fun updateNotification() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, createNotification())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "cooking_timer_channel",
                "Cooking Timer",
                NotificationManager.IMPORTANCE_LOW
            )

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}