package com.ethereallab.fb_todo.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.ethereallab.fb_todo.PlanIngredientsActivity
import com.ethereallab.fb_todo.adapters.PlanAdapter
import com.ethereallab.fb_todo.adapters.RecipeAdapter
import com.ethereallab.fb_todo.databinding.FragmentCreateBinding
import com.ethereallab.fb_todo.databinding.FragmentMealsBinding
import com.ethereallab.fb_todo.models.Recipe
import com.ethereallab.fb_todo.models.Todo
import com.ethereallab.fb_todo.repository.ToDoRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.Headers
import org.json.JSONException
import java.time.LocalDate
import java.util.HashMap

class MealsFragment : Fragment() {

    private var _binding: FragmentMealsBinding? = null
    private val binding get() = _binding!!
    private lateinit var planRecyclerView: RecyclerView
    private val database = Firebase.database
    private val cart = mutableListOf<Recipe>()
    private val plan_recipes = mutableListOf<Recipe>()
    private var all_ingredients = mutableListOf<String>()
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealsBinding.inflate(inflater, container, false)

        planRecyclerView = binding.plansRecyclerView
        val mainActivityContext = requireActivity()
        val planAdapter = PlanAdapter(mainActivityContext,this, plan_recipes)
        planRecyclerView.adapter = planAdapter
//        recipesRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//            .also {
//                val dividerItemDecoration = DividerItemDecoration(context, it.orientation)
//                recipesRecyclerView.addItemDecoration(dividerItemDecoration)
//            }
        planRecyclerView.layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
            .also { gridLayoutManager ->
                val dividerItemDecoration = DividerItemDecoration(context, gridLayoutManager.orientation)
                planRecyclerView.addItemDecoration(dividerItemDecoration)
            }


/*
* if () {
            binding.emptyMessage.visibility = VISIBLE
        } else {}
* */
        // TODO: SEE PLAN BUTTON
//        button.setOnClickListener {

            plan_recipes.clear()
            planAdapter.notifyDataSetChanged()
            val db = Firebase.firestore
            var user_id = (auth.currentUser?.uid ?: 0)
            val plansCollection = db.collection("plans")
            val query = plansCollection.whereEqualTo("Active", "true")
                .whereEqualTo("User Id",user_id.toString())
            // TODO: Query will output 1 document only, the active plan.
            query.get().addOnSuccessListener { documents ->
                for (document in documents) {
                    val ten_day_meal_plan = document.data
//                    println("This is my plan: " + ten_day_meal_plan)
                    var hashmapRecipes: MutableMap<String, Any>
                    hashmapRecipes = ten_day_meal_plan.get("Recipes") as MutableMap<String, Any>

                    all_ingredients = ten_day_meal_plan.get("Grocery List") as MutableList<String>

                    println("A meal plan")
//                    println("All recipes for current meal plan: " + hashmapRecipes)

                    for (i in 0 .. 9) {
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
                        println("Recipe meal for a day: " + recipe_obj)
                        plan_recipes.add(recipe_obj)
                    }
                    // TODO: ALL INGREDIENTS BUTTON
                    val viewIngredientsButton = binding.allIngredientsButton
                    viewIngredientsButton.visibility = VISIBLE
                    println("ALL INGREDIENTS" + all_ingredients)

                    viewIngredientsButton.setOnClickListener{
                        val intent = Intent(requireActivity(), PlanIngredientsActivity::class.java)
                        intent.putStringArrayListExtra("myListKey",ArrayList(all_ingredients))
                        startActivity(intent)
                    }
                }
                planAdapter.notifyDataSetChanged()


            }.addOnFailureListener {
                exception ->
                println("Error retrieving plans: " + exception)
                binding.emptyMessage.visibility = VISIBLE
            }
//        }


        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
