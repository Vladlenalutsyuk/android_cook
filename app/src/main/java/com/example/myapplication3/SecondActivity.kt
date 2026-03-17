package com.example.myapplication3
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val textViewRecipeName = findViewById<TextView>(R.id.textViewRecipeName)
        val textViewIngredients = findViewById<TextView>(R.id.textViewIngredients)
        val textViewDescription = findViewById<TextView>(R.id.textViewDescription)
        val buttonAddRecipe = findViewById<Button>(R.id.buttonAddRecipe)

        val recipeName = intent.getStringExtra("recipe_name")
        val ingredients = intent.getStringExtra("ingredients")
        val description = intent.getStringExtra("description")

        textViewRecipeName.text = "Название рецепта: $recipeName"
        textViewIngredients.text = "Ингредиенты: $ingredients"
        textViewDescription.text = "Описание: $description"

        buttonAddRecipe.setOnClickListener {

            val resultIntent = Intent()

            resultIntent.putExtra(
                "result_message",
                "Рецепт успешно добавлен"
            )

            setResult(RESULT_OK, resultIntent)

            finish()
        }
    }
}