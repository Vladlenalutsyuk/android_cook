package com.example.myapplication3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeHelper.applySavedTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextRecipeName = findViewById<EditText>(R.id.editTextRecipeName)
        val buttonFindRecipe = findViewById<Button>(R.id.buttonFindRecipe)
        val buttonAddRecipe = findViewById<Button>(R.id.buttonAddRecipe)
        val switchTheme = findViewById<SwitchCompat>(R.id.switchTheme)

        switchTheme.isChecked = ThemeHelper.isDarkMode(this)

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            ThemeHelper.saveTheme(this, isChecked)
            recreate()
        }

        buttonFindRecipe.setOnClickListener {
            val recipeName = editTextRecipeName.text.toString().trim()

            if (recipeName.isEmpty()) {
                Toast.makeText(this, getString(R.string.enter_recipe_name), Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, RecipeDetailsActivity::class.java)
                intent.putExtra("recipe_name", recipeName)
                startActivity(intent)
            }
        }

        buttonAddRecipe.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            startActivity(intent)
        }
    }
}