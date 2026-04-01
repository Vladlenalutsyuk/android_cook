package com.example.myapplication3

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlin.concurrent.thread

class RecipeSaveService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val recipeName = intent?.getStringExtra("recipe_name") ?: "Без названия"

        thread {
            Log.d("RecipeSaveService", "Начато сохранение рецепта: $recipeName")

            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            Log.d("RecipeSaveService", "Рецепт сохранён: $recipeName")
            stopSelf()
        }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}