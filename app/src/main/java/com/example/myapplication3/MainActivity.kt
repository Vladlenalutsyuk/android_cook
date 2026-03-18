package com.example.myapplication3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currentRecipeName: String = ""
    private var currentRecipeIngredients: String = ""

    private val addRecipeLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                currentRecipeName = data?.getStringExtra("recipe_name") ?: ""
                currentRecipeIngredients = data?.getStringExtra("recipe_ingredients") ?: ""
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOpenRecipe = findViewById<Button>(R.id.btnOpenRecipe)
        val btnAddRecipe = findViewById<Button>(R.id.btnAddRecipe)

        currentRecipeName = getString(R.string.sample_recipe)
        currentRecipeIngredients = getString(R.string.sample_ingredients)

        btnOpenRecipe.setOnClickListener {
            val intent = Intent(this, RecipeDetailsActivity::class.java)
            intent.putExtra("recipe_name", currentRecipeName)
            intent.putExtra("recipe_ingredients", currentRecipeIngredients)
            startActivity(intent)
        }

        btnAddRecipe.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            addRecipeLauncher.launch(intent)
        }
    }
}