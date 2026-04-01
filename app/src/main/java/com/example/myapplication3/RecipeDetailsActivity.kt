package com.example.myapplication3

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var recipeTitle: TextView
    private lateinit var recipeIngredients: TextView
    private lateinit var timerTextView: TextView
    private lateinit var startTimerButton: Button
    private lateinit var stopTimerButton: Button

    private var cookingTimerService: CookingTimerService? = null
    private var isBound = false

    private val handler = Handler(Looper.getMainLooper())

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as CookingTimerService.LocalBinder
            cookingTimerService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
            cookingTimerService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeHelper.applySavedTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        recipeTitle = findViewById(R.id.recipeTitle)
        recipeIngredients = findViewById(R.id.recipeIngredients)
        timerTextView = findViewById(R.id.timerTextView)
        startTimerButton = findViewById(R.id.startTimerButton)
        stopTimerButton = findViewById(R.id.stopTimerButton)

        val selectedRecipe = intent.getStringExtra("recipe_name")

        if (!selectedRecipe.isNullOrEmpty()) {
            recipeTitle.text = selectedRecipe
        }

        recipeIngredients.text = getString(R.string.sample_ingredients)

        startTimerButton.setOnClickListener {
            requestNotificationPermissionIfNeeded()

            val timerIntent = Intent(this, CookingTimerService::class.java)
            timerIntent.putExtra("time_seconds", 60)
            startService(timerIntent)

            if (!isBound) {
                bindService(timerIntent, connection, Context.BIND_AUTO_CREATE)
            }

            handler.postDelayed({
                updateTimerUI()
            }, 200)
        }

        stopTimerButton.setOnClickListener {
            cookingTimerService?.stopTimer()
            timerTextView.text = getString(R.string.timer_stopped)
            handler.removeCallbacksAndMessages(null)
        }
    }

    override fun onStart() {
        super.onStart()

        val timerIntent = Intent(this, CookingTimerService::class.java)
        bindService(timerIntent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()

        handler.removeCallbacksAndMessages(null)

        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }

    private fun updateTimerUI() {
        handler.removeCallbacksAndMessages(null)

        handler.post(object : Runnable {
            override fun run() {
                val service = cookingTimerService

                if (isBound && service != null) {
                    timerTextView.text = getString(
                        R.string.timer_seconds_format,
                        service.getTimeLeft()
                    )

                    if (service.isTimerRunning()) {
                        handler.postDelayed(this, 1000)
                    }
                }
            }
        })
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!granted) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }
    }
}