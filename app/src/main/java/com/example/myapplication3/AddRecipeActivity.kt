package com.example.myapplication3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddRecipeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        val editRecipeName = findViewById<EditText>(R.id.editRecipeName)
        val editIngredients = findViewById<EditText>(R.id.editIngredients)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val name = editRecipeName.text.toString().trim()
            val ingredients = editIngredients.text.toString().trim()

            if (name.isEmpty() || ingredients.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            } else {
                val resultIntent = Intent()
                resultIntent.putExtra("recipe_name", name)
                resultIntent.putExtra("recipe_ingredients", ingredients)

                setResult(Activity.RESULT_OK, resultIntent)
                Toast.makeText(this, "Рецепт сохранён", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}