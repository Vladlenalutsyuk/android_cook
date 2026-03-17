package com.example.myapplication3
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_RECIPE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextRecipeName = findViewById<EditText>(R.id.editTextRecipeName)
        val editTextIngredients = findViewById<EditText>(R.id.editTextIngredients)
        val editTextDescription = findViewById<EditText>(R.id.editTextDescription)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)

        val buttonSend = findViewById<Button>(R.id.buttonSend)
        val buttonOpenSite = findViewById<Button>(R.id.buttonOpenSite)

        buttonSend.setOnClickListener {

            val recipeName = editTextRecipeName.text.toString()
            val ingredients = editTextIngredients.text.toString()
            val description = editTextDescription.text.toString()

            val intent = Intent(this, SecondActivity::class.java)

            intent.putExtra("recipe_name", recipeName)
            intent.putExtra("ingredients", ingredients)
            intent.putExtra("description", description)

            startActivityForResult(intent, REQUEST_CODE_RECIPE)
        }

        buttonOpenSite.setOnClickListener {

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://eda.ru/recepty")
            )

            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_RECIPE && resultCode == RESULT_OK) {

            val result = data?.getStringExtra("result_message")
            val textViewResult = findViewById<TextView>(R.id.textViewResult)

            textViewResult.text = result
        }
    }
}