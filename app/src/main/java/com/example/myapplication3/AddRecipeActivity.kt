package com.example.myapplication3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddRecipeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeHelper.applySavedTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        val editTextName = findViewById<EditText>(R.id.editRecipeName)
        val editTextIngredients = findViewById<EditText>(R.id.editIngredients)
        val buttonSave = findViewById<Button>(R.id.btnSave)

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val ingredients = editTextIngredients.text.toString().trim()

            if (name.isEmpty() || ingredients.isEmpty()) {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
            } else {
                val serviceIntent = Intent(this, RecipeSaveService::class.java)
                serviceIntent.putExtra("recipe_name", name)
                startService(serviceIntent)

                Toast.makeText(this, getString(R.string.recipe_saved), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}