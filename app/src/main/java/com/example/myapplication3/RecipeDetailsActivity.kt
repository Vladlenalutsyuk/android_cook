package com.example.myapplication3

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RecipeDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeHelper.applySavedTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        val recipeTitle = findViewById<TextView>(R.id.recipeTitle)
        val recipeIngredients = findViewById<TextView>(R.id.recipeIngredients)

        val selectedRecipe = intent.getStringExtra("recipe_name")

        if (!selectedRecipe.isNullOrEmpty()) {
            recipeTitle.text = selectedRecipe
        }
    }
}