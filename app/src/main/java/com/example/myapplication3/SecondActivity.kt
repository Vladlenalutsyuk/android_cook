package com.example.myapplication3

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val textViewRecipeName = findViewById<TextView>(R.id.textViewRecipeName)
        val textViewIngredients = findViewById<TextView>(R.id.textViewIngredients)
        val textViewStatus = findViewById<TextView>(R.id.textViewStatus)

        val recipeName = intent.getStringExtra("recipe_name")
        val ingredients = intent.getStringExtra("ingredients")

        textViewRecipeName.text = recipeName
        textViewIngredients.text = ingredients
        textViewStatus.text = getString(R.string.recipe_added_success)
    }
}