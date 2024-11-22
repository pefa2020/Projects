package com.example.bitfit

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
//import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import com.example.bitfit.databinding.ActivityMainBinding
import okhttp3.Headers
import org.json.JSONException


import androidx.appcompat.app.ActionBarDrawerToggle
//import androidx.compose.ui.semantics.text
//import androidx.compose.foundation.text2.input.insert
//import androidx.compose.ui.semantics.text

//import androidx.glance.visibility
//import androidx.compose.ui.semantics.text
//import androidx.glance.visibility
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

class MainActivity : AppCompatActivity() {

    private lateinit var ediblesRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private val edibles = mutableListOf<DisplayEdible>()
    private lateinit var edibleAdapter: EdibleAdapter
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ediblesRecyclerView = findViewById(R.id.ediblesRv)

        edibleAdapter = EdibleAdapter(this, edibles, lifecycleScope) { edibleId ->
            // Handle item long click here
            lifecycleScope.launch(Dispatchers.IO) {
                (application as EdibleApplication).db.edibleDao().deleteById(edibleId)
                // ... (Optional) Update UI on main thread ...
            }
        }


        ediblesRecyclerView.adapter = edibleAdapter

        ediblesRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            ediblesRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        submitButton = findViewById(R.id.submit)

        lifecycleScope.launch {
//            fetchEdibles()
            (application as EdibleApplication).db.edibleDao().getAll().collect { databaseList ->
                updateEdibles(databaseList)
            }
        }

        println("Reached here")
        val inputName = findViewById<EditText>(R.id.inputName)
        val inputCalories = findViewById<EditText>(R.id.inputCalories)

        submitButton.setOnClickListener {
            println("YOu clicked submit")
            val name = inputName.text.toString()
            val calories = inputCalories.text.toString()

            if (name.isNotEmpty() && calories.isNotEmpty()) {
                // proceed to add to list
                println("Name: $name")
                println("Calories: $calories")
                // Hide virtual keyboard
                inputName.onEditorAction(6)
                inputCalories.onEditorAction(6)

                println("GOod till here")
                lifecycleScope.launch(Dispatchers.IO) {
                    (application as EdibleApplication).db
                        .edibleDao().insert(
                        EdibleEntity(name = name, calories = calories.toInt(), mediaImageUrl = "")
                    )

                    withContext(Dispatchers.Main) {
                        inputName.text.clear()
                        inputCalories.text.clear()
                        // ... other UI updates ...
                    }

                    // Update the RecyclerView
                //                    lifecycleScope.launch {
//                        (application as EdibleApplication).db.edibleDao().getAll().collect { databaseList ->
//                            updateEdibles(databaseList)
//                        }
//                    }

                }
            }

        }

    }

    private suspend fun updateEdibles(databaseList: List<EdibleEntity>) {
        val mappedList = databaseList.map { entity ->
            DisplayEdible(
                entity.id,
                entity.name,
                entity.calories,
                entity.mediaImageUrl
            )
        }
        withContext(Dispatchers.Main) {
            edibles.clear()
            edibles.addAll(mappedList)
            edibleAdapter.notifyDataSetChanged()
        }
    }

    private fun fetchEdibles() {
        val client = AsyncHttpClient()
        /* Button on submit button click
        *
        * (application as ArticleApplication).db.articleDao().insertAll(
                                    list.map {
                                        EdibleEntity(
                                            headline = it.headline?.main,
                                            articleAbstract = it.abstract,
                                            byline = it.byline?.original,
                                            mediaImageUrl = it.mediaImageUrl
                                        )
                                    })
        * */

//        submitButton.setOnClickListener {
//            // Handle button click here
//            val name = binding.inputName.text.toString()
//            val calories = binding.inputCalories.text.toString().toIntOrNull()// Handle null or empty input
//
//            if (name != "" && calories != null) {
//                println("Something happens")
//                lifecycleScope.launch {
//                    (application as EdibleApplication).db.edibleDao().insert(
//                        EdibleEntity(name = name, calories = calories, mediaImageUrl = "")
//                    )
//                }
//
//                // Clear the input fields
//                binding.inputName.text.clear()
//                binding.inputCalories.text.clear()
//
//                // Update the RecyclerView
//                lifecycleScope.launch {
//                    (application as EdibleApplication).db.edibleDao().getAll().collect { databaseList ->
//                        updateEdibles(databaseList)
//                    }
//                }
//            } else {
//                println("Nothing happens")
//            }
//            // Create a new EdibleEntity and insert it into the database
//
//        }



    }
}