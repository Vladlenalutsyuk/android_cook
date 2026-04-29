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
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso

class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var recipeTitle: TextView
    private lateinit var recipeCategory: TextView
    private lateinit var recipeIngredients: TextView
    private lateinit var recipeInstructions: TextView
    private lateinit var recipeImage: ImageView
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var errorTextView: TextView

    private lateinit var timerTextView: TextView
    private lateinit var startTimerButton: Button
    private lateinit var stopTimerButton: Button

    private lateinit var viewModel: RecipeViewModel

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
        recipeCategory = findViewById(R.id.recipeCategory)
        recipeIngredients = findViewById(R.id.recipeIngredients)
        recipeInstructions = findViewById(R.id.recipeInstructions)
        recipeImage = findViewById(R.id.recipeImage)
        loadingProgressBar = findViewById(R.id.loadingProgressBar)
        errorTextView = findViewById(R.id.errorTextView)

        timerTextView = findViewById(R.id.timerTextView)
        startTimerButton = findViewById(R.id.startTimerButton)
        stopTimerButton = findViewById(R.id.stopTimerButton)

        val database = RecipeDatabase.getDatabase(this)
        val repository = RecipeRepository(database.recipeDao())
        val factory = RecipeViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[RecipeViewModel::class.java]

        observeViewModel()

        val selectedRecipe = intent.getStringExtra("recipe_name")?.trim().orEmpty()

        if (selectedRecipe.isEmpty()) {
            showError(getString(R.string.enter_recipe_name))
        } else {
            viewModel.loadRecipe(selectedRecipe)
        }

        startTimerButton.setOnClickListener {
            requestNotificationPermissionIfNeeded()

            val timerIntent = Intent(this, CookingTimerService::class.java)
            timerIntent.putExtra("time_seconds", 60)
            startService(timerIntent)

            if (!isBound) {
                bindService(timerIntent, connection, Context.BIND_AUTO_CREATE)
            }

            handler.postDelayed({ updateTimerUI() }, 200)
        }

        stopTimerButton.setOnClickListener {
            cookingTimerService?.stopTimer()
            timerTextView.text = getString(R.string.timer_stopped)
            handler.removeCallbacksAndMessages(null)
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { message ->
            if (message != null) {
                showError(message)
            } else {
                errorTextView.visibility = View.GONE
            }
        }

        viewModel.recipe.observe(this) { recipe ->
            if (recipe != null) {
                showRecipe(recipe)
            }
        }
    }

    private fun showRecipe(recipe: RecipeEntity) {
        errorTextView.visibility = View.GONE

        recipeTitle.text = recipe.title
        recipeCategory.text = getString(
            R.string.recipe_meta_format,
            recipe.category,
            recipe.area
        )
        recipeIngredients.text = recipe.ingredients.ifEmpty { getString(R.string.no_data) }
        recipeInstructions.text = recipe.instructions.ifEmpty { getString(R.string.no_data) }

        if (recipe.imageUrl.isNotEmpty()) {
            Picasso.get()
                .load(recipe.imageUrl)
                .placeholder(R.drawable.default_recipe)
                .error(R.drawable.default_recipe)
                .into(recipeImage)
        } else {
            recipeImage.setImageResource(R.drawable.default_recipe)
        }

        recipeImage.scaleType = ImageView.ScaleType.CENTER_CROP
    }

    private fun showError(message: String) {
        errorTextView.visibility = View.VISIBLE
        errorTextView.text = message

        recipeTitle.text = getString(R.string.no_data)
        recipeCategory.text = ""
        recipeIngredients.text = ""
        recipeInstructions.text = ""

        recipeImage.setImageResource(R.drawable.default_recipe)
        recipeImage.scaleType = ImageView.ScaleType.CENTER_CROP
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