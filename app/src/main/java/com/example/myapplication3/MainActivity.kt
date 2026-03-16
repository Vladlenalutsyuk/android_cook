package com.example.myapplication3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextRecipeName: EditText
    private lateinit var editTextIngredients: EditText
    private lateinit var buttonAddRecipe: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MainActivity", "onCreate")

        editTextRecipeName = findViewById(R.id.editTextRecipeName)
        editTextIngredients = findViewById(R.id.editTextIngredients)
        buttonAddRecipe = findViewById(R.id.buttonAddRecipe)

        buttonAddRecipe.setOnClickListener {
            val recipeName = editTextRecipeName.text.toString()
            val ingredients = editTextIngredients.text.toString()

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("recipe_name", recipeName)
            intent.putExtra("ingredients", ingredients)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("saved_recipe_name", editTextRecipeName.text.toString())
        outState.putString("saved_ingredients", editTextIngredients.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        editTextRecipeName.setText(savedInstanceState.getString("saved_recipe_name", ""))
        editTextIngredients.setText(savedInstanceState.getString("saved_ingredients", ""))
    }
}