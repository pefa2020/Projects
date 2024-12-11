package com.ethereallab.fb_todo.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ethereallab.fb_todo.LoginActivity
import com.ethereallab.fb_todo.R
import com.ethereallab.fb_todo.databinding.FragmentHomeBinding
import com.ethereallab.fb_todo.models.Recipe
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        println("This is your user Id: " + (auth.currentUser?.uid ?: 0))
        var user_id = (auth.currentUser?.uid ?: 0)
        val db = Firebase.firestore
        val current_date = Timestamp.now()
        val plansCollection = db.collection("plans")
        // TODO: Query that matches current day to a Plan of that ranges between a start_date & end_date
        val query = plansCollection.whereEqualTo("Active", "true")
            .whereEqualTo("User Id", user_id.toString())
            .whereLessThanOrEqualTo("date Start", current_date) // Start date before or on today
            .whereGreaterThanOrEqualTo("date End", current_date) // End date after or on today
//
        println("This is a query: "+query)
        query.get().addOnSuccessListener { documents ->
            if (documents.isEmpty()) {
                // Show Toast message if no documents found
                Toast.makeText(requireContext(), "No active plans found for today.", Toast.LENGTH_SHORT).show()
                binding.emptyMessageDaily.visibility = VISIBLE
                binding.dailyIngredients.text = ""
                binding.dailyRecipeText.text = ""

            } else {
                // Process the documents as before
                binding.dailyRecipeTitle.visibility=VISIBLE
                binding.visibleIngredients.visibility= VISIBLE
                binding.visibleRecipe.visibility=VISIBLE
                for (document in documents) {

                val ten_day_meal_plan = document.data
                println(ten_day_meal_plan)
                val startDate = ten_day_meal_plan["date Start"] as Timestamp
                val endDate = ten_day_meal_plan["date End"] as Timestamp

                // Index (0-9) where the current_date falls between start_date: 0 and end_date: 9
                val index = (current_date.seconds - startDate.seconds) / (24 * 60 * 60)

                println("INDEX IS: "+index) // 0

                var hashmapRecipes: MutableMap<String, Any>
                hashmapRecipes = ten_day_meal_plan.get("Recipes") as MutableMap<String, Any>
                // Get Recipe at index and display daily Recipe
                for (i in 0 .. 9) {
                    if (i.toLong() == index) {

                        var a_recipe_of_plan: MutableMap<String, Any>
                        a_recipe_of_plan = hashmapRecipes[i.toString()] as MutableMap<String, Any>

                        var a_recipe_ingredients: MutableList<String>
                        a_recipe_ingredients = a_recipe_of_plan.get("ingredients") as MutableList<String>

                        val recipe_obj = Recipe(
                            a_recipe_of_plan.get("title").toString(),
                            a_recipe_of_plan.get("instructions").toString(),
                            a_recipe_ingredients,
                            a_recipe_of_plan.get("poster_path").toString(),
                            a_recipe_of_plan.get("recipe_id").toString()
                        )

                        val bulletPoints = if (a_recipe_ingredients != null) {
                            a_recipe_ingredients.map { "\u2022 $it" }.joinToString("\n")
                        } else {
                            "No ingredients found."
                        }
                        println("Recipe meal for a day: " + recipe_obj)
                        // Display daily recipe
                        println("IT'S A MATCH")
                        var recipeImageView: ImageView
                        var titleTextView: TextView
                        var ingredientsTextView: TextView
                        var recipeTextView: TextView
                        recipeImageView = binding.dailyRecipeImage
                        titleTextView = binding.dailyRecipeTitle
                        ingredientsTextView = binding.dailyIngredients
                        recipeTextView =  binding.dailyRecipeText

                        titleTextView.text = a_recipe_of_plan.get("title").toString()
                        ingredientsTextView.text = bulletPoints
                        recipeTextView.text = a_recipe_of_plan.get("instructions").toString()
                        Glide.with(this)
                            .load(a_recipe_of_plan.get("poster_path").toString())
                            .into(recipeImageView)


                        println("DONE!!")

                    }
                }



            }
            }
        }.addOnFailureListener { exception ->
            // Handle any errors
            println("ERROR: "+exception)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
