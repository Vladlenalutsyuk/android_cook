package com.example.myapplication3

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RecipeDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        val recipeTitle = findViewById<TextView>(R.id.recipeTitle)
        val recipeIngredients = findViewById<TextView>(R.id.recipeIngredients)

        val name = intent.getStringExtra("recipe_name")
        val ingredients = intent.getStringExtra("recipe_ingredients")

        recipeTitle.text = name
        recipeIngredients.text = ingredients
    }
}