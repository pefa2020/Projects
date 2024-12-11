package com.ethereallab.fb_todo

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import androidx.compose.ui.semantics.setText
import com.bumptech.glide.Glide
//import com.ethereallab.fb_todo.ARTICLE_EXTRA
import com.ethereallab.fb_todo.models.Recipe
import com.ethereallab.fb_todo.R
import com.ethereallab.fb_todo.adapters.RECIPE_EXTRA

//import com.ethereallab.fb_todo.SHOW_EXTRA
//import com.ethereallab.fb_todo.Shows

private const val TAG = "DetailActivity"

class PlanIngredientsActivity : AppCompatActivity() {
    private lateinit var recipeImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var ingredientsTextView: TextView
    private lateinit var messageEditText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_ingredients)

        /* FOR RECIPES */
//        // TODO: Find the views for the screen
        titleTextView = findViewById(R.id.recipeTitle)
        ingredientsTextView = findViewById(R.id.allIngredients)
//        messageEditText = findViewById(R.id.etMessage)

//        // TODO: Get the extra from the Intent
        val receivedList: ArrayList<String>? = intent.getStringArrayListExtra("myListKey")
        println("Received List: " + receivedList)

        val bulletPoints = if (receivedList != null) {
            receivedList.map { "\u2022 $it" }.joinToString("\n")
        } else {
            "No ingredients found."
        }

        ingredientsTextView.text = bulletPoints



        // TODO: Send Email with Grocery List
        val recipientEditText = findViewById<EditText>(R.id.etRecipientEmail)
        val subjectEditText = findViewById<EditText>(R.id.etSubject)
        val theMessageEditText =  bulletPoints
        val sendEmailButton = findViewById<Button>(R.id.btnSendEmail)

        sendEmailButton.setOnClickListener {
            val recipient = recipientEditText.text.toString().trim()
            val subject = subjectEditText.text.toString().trim()
            val message = theMessageEditText.toString().trim()

            val mIntent = Intent(Intent.ACTION_SEND)

            mIntent.data = Uri.parse("mailto:")
            mIntent.type = "text/plain"
            mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            mIntent.putExtra(Intent.EXTRA_TEXT, message)

            try {
                startActivity(Intent.createChooser(mIntent, "Send Email"))
            }catch (e: Exception){
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }


    }
}