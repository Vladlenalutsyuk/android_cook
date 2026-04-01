package com.example.myapplication3

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

class RecipeSaveService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Рецепт сохранён", Toast.LENGTH_SHORT).show()
        stopSelf()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}