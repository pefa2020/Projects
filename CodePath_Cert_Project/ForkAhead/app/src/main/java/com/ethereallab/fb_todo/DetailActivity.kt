package com.ethereallab.fb_todo

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
//import com.ethereallab.fb_todo.ARTICLE_EXTRA
import com.ethereallab.fb_todo.models.Recipe
import com.ethereallab.fb_todo.R
import com.ethereallab.fb_todo.adapters.RECIPE_EXTRA

//import com.ethereallab.fb_todo.SHOW_EXTRA
//import com.ethereallab.fb_todo.Shows

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {
    private lateinit var recipeImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var ingredientsTextView: TextView
    private lateinit var recipeTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        /* FOR RECIPES */
//        // TODO: Find the views for the screen
        recipeImageView = findViewById(R.id.recipeImage)
        titleTextView = findViewById(R.id.recipeTitle)
        ingredientsTextView = findViewById(R.id.ingredients)
        recipeTextView = findViewById(R.id.recipeText)

//        // TODO: Get the extra from the Intent
        val recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(RECIPE_EXTRA, Recipe::class.java) as? Recipe

        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(RECIPE_EXTRA) as? Recipe
        }

        // TODO: Set the title, byline, and abstract information from the article
        if (recipe != null) {
            titleTextView.text = recipe.title
            ingredientsTextView.text = recipe.ingredients.toString()
            recipeTextView.text = recipe.instructions
        }
        // TODO: Load the media image
        if (recipe != null) {
            Glide.with(this)
                .load(recipe.poster_path)
                .into(recipeImageView)
        }


    }
}