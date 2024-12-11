package com.ethereallab.fb_todo.fragments

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
//import androidx.compose.animation.with
//import androidx.compose.ui.semantics.text
import com.google.firebase.Timestamp
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.ethereallab.fb_todo.R
import com.ethereallab.fb_todo.adapters.RecipeAdapter
import com.ethereallab.fb_todo.databinding.FragmentCreateBinding
import com.ethereallab.fb_todo.models.Recipe
import com.ethereallab.fb_todo.models.Todo
import com.ethereallab.fb_todo.repository.ToDoRepository
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import okhttp3.Headers
import org.json.JSONException
import androidx.core.content.ContentProviderCompat.requireContext
//import androidx.glance.visibility
import androidx.lifecycle.Lifecycle
//import androidx.glance.visibility
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import java.time.DayOfWeek
import com.google.firebase.firestore.ktx.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.util.Date
import java.util.HashMap
import java.util.concurrent.TimeUnit

private const val TAG = "CreateFragment: "

class CreateFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null
    private lateinit var recipesRecyclerView: RecyclerView
    private val binding get() = _binding!!
    private lateinit var repository: ToDoRepository
    private val database = Firebase.database
    private val cart = mutableListOf<Recipe>()
    private val recipes = mutableListOf<Recipe>()
    val cartSizeFlow = MutableStateFlow(cart.size)
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }



    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)

        recipesRecyclerView = binding.recipesRecyclerView
        val mainActivityContext = requireActivity()
        val recipeAdapter = RecipeAdapter(mainActivityContext, this, recipes, cart)
        recipesRecyclerView.adapter = recipeAdapter
//        recipesRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//            .also {
//                val dividerItemDecoration = DividerItemDecoration(context, it.orientation)
//                recipesRecyclerView.addItemDecoration(dividerItemDecoration)
//            }
        recipesRecyclerView.layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
            .also { gridLayoutManager ->
                val dividerItemDecoration = DividerItemDecoration(context, gridLayoutManager.orientation)
                recipesRecyclerView.addItemDecoration(dividerItemDecoration)
            }

        val button = binding.searchButton
//        val QUERY = binding.searchEditText.text
        val client = AsyncHttpClient()

        // TODO: SEARCH BUTTON
        button.setOnClickListener {

            recipes.clear()
            recipeAdapter.notifyDataSetChanged()
            val query = binding.searchEditText.text.toString() // Get text from EditText
            val RECIPE_SEARCH_URL = "https://www.themealdb.com/api/json/v1/1/search.php?s=$query"

            if (query.isNotEmpty()) {
                // LOAD AND DISPLAY RECIPES IN RV
                client.get(RECIPE_SEARCH_URL, object : JsonHttpResponseHandler() {
                    override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                        Log.i(TAG, "Successfully fetched recipes: $json")
                        try {
                            val mealsArray = json.jsonObject.getJSONArray("meals")
                            for (i in 0 until mealsArray.length()) {
                                val mealJson = mealsArray.getJSONObject(i)
                                val title = mealJson.getString("strMeal")
                                val instructions = mealJson.getString("strInstructions")
                                val image = mealJson.getString("strMealThumb")
                                val meal_id = mealJson.getString("idMeal")

                                // Extract ingredients
                                val ingredients = mutableListOf<String>()
                                for (j in 1..20) {
                                 val ingredient = mealJson.optString("strIngredient$j")
                                    val quantity = mealJson.optString("strMeasure$j")
                                    if (ingredient.isNotEmpty() && ingredient != "null") {
                                        if (quantity.isNotEmpty() && quantity != "null") {
                                            ingredients.add(ingredient+" "+quantity)
                                        }
                                    }
                                }

                                println("THE TITLE: $title")
                                println("The INGREDIENTS: $ingredients")
                                println("The INSTRUCTIONS: $instructions")
                                println("The ID: $meal_id")

                                // Create Recipe object and add to your list
                                val recipe = Recipe(title,instructions,ingredients, image, meal_id)
                                recipes.add(recipe)

                            }
                            // Notify your adapter
                            recipeAdapter.notifyDataSetChanged()

                        } catch (e: JSONException) {
                            Log.e(TAG, "Exception: $e", e)
                        }
                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String?,
                        throwable: Throwable?
                    ) {
                        Log.e(TAG, "Failed to fetch recipes: $statusCode")
                    }
                })

            }

        }
        // TODO: ADD_PLAN BUTTON
        val add_plan_button = binding.addPlanButton
        val database = Firebase.firestore

        val userId = (auth.currentUser?.uid ?: 0).toString()
        var have_active_plan = false
        database.collection("plans")
            .whereEqualTo("User Id", userId)
            .whereEqualTo("Active", "true")
            .get().addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    have_active_plan = false

                } else {
                    have_active_plan = true
                }
                // Means we are unable to add plan since there's already an active one
            }




        add_plan_button.setOnClickListener {
//            val recipe = Recipe(
//                title = "Delicious Dish",
//                instructions = "Follow these steps...",
//                ingredients = mutableListOf("Ingredient 1", "Ingredient 2"),
//                poster_path = "path/to/image",
//                recipe_id = "recipe123"
//            )
            if (have_active_plan == true) {
                Toast.makeText(requireContext(), "You already have an active plan", Toast.LENGTH_SHORT)
                    .show()
                if (have_active_plan == true) {
                    println("YOU HAVE AN ACTIVE PLAN")
                } else {
                    println("YOU DO NOT HAVE AN ACTIVE PLAN")
                }
            }
            else {
            val all_ingredients: MutableList<String> = mutableListOf()
            for (i in cart) {
                for (j in i.ingredients!!) {
                    all_ingredients.add(j)
                }
            }

            val hashmapRecipes = hashMapOf<String, HashMap<String, Any?>>()

            for (i in cart.indices) {
                val recipe = cart[i]
                hashmapRecipes[i.toString()] = hashMapOf(
                    "ingredients" to recipe.ingredients,
                    "instructions" to recipe.instructions,
                    "poster_path" to recipe.poster_path,
                    "recipe_id" to recipe.recipe_id,
                    "title" to recipe.title
                )
            }


//            val (startDate, endDate) = createMealPlan(LocalDate.now())
            val startDate = Timestamp.now()
            val endDate =
                Timestamp(startDate.seconds + TimeUnit.DAYS.toSeconds(10), startDate.nanoseconds)

            val data = hashMapOf(
                "User Id" to (auth.currentUser?.uid ?: 0).toString(),
                "Week" to 1,
                "date Start" to startDate, // Current date
                "date End" to endDate, // Date one week from now
                "Recipes" to hashmapRecipes,
                "Grocery List" to all_ingredients,
                "Active" to "true"
            )
            database.collection("plans")
                .add(data)
                .addOnSuccessListener { documentReference ->
                    Log.d("Firebase", "DocumentSnapshot added with ID: ${documentReference.id}")
                    cart.clear()
                    add_plan_button.visibility = View.INVISIBLE
                }
                .addOnFailureListener { e ->
                    Log.w("Firebase", "Error adding document", e)
                }

        }
        }

        return binding.root
    }

    fun createMealPlan(inputDate: LocalDate): Pair<LocalDate, LocalDate> {
        val nextMonday = inputDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))
        val endWednesday = nextMonday.plusDays(9) // Wednesday is 9 days after Monday
        return Pair(nextMonday, endWednesday)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        lifecycleScope.launch {
//            while(isActive ) {
//                delay(500)
//                binding.addPlanButton.visibility = if (cart.size >= 10) View.VISIBLE else View.INVISIBLE
//            }
//        }
        lifecycleScope.launch {
            cartSizeFlow.collect { cartSize ->
                binding.addPlanButton.visibility = if (cartSize == 10) View.VISIBLE else View.INVISIBLE
            }
        }
    }

        override fun onDestroyView() {
        super.onDestroyView()
            cart.clear()

            println("SIZE OF CART DESTROYED: " + cart.size)
        _binding = null
    }


//
//    private fun observeCompletedTodos() {
//        lifecycleScope.launch {
//            repository.getCompletedTodos().collect { todos ->
//                if (_binding != null) { // Check if binding is still valid
//                    completedTodos.clear()
//                    completedTodos.addAll(todos)
//                    todoAdapter.notifyDataSetChanged()
//
//                    // Show emptyTextView if the list is empty
//                    binding.emptyTextView.visibility = if (todos.isEmpty()) View.VISIBLE else View.GONE
//                }
//            }
//        }
    }

    // Show confirmation dialog for un-completing a todo
//    private fun showMarkNotDoneDiagog(todo: Todo) {
//        AlertDialog.Builder(requireContext())
//            .setTitle("Mark as Pending")
//            .setMessage("Do you want to mark this TODO as pending?")
//            .setPositiveButton("Yes") { _, _ ->
//                markToDoNotDone(todo)
//            }
//            .setNegativeButton("No", null)
//            .show()
//    }
//    private fun markToDoNotDone(todo: Todo) {
//        val updatedTodo = todo.copy(isDone = false, completedDate = null) // Mark as pending
//        lifecycleScope.launch {
//            try {
//                repository.updateTodoStatus(updatedTodo)
//                Toast.makeText(requireContext(), "TODO marked as pending", Toast.LENGTH_SHORT).show()
//            } catch (e: Exception) {
//                Toast.makeText(requireContext(), "Failed to mark TODO as pending", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
