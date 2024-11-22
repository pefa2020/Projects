package com.example.flixster

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.bestsellerlistapp.Flixster
import com.codepath.bestsellerlistapp.FlixsterRecyclerViewAdapter
import com.codepath.bestsellerlistapp.Shows
import com.codepath.bestsellerlistapp.ShowsRecyclerViewAdapter
import com.example.flixster.R.id
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselStrategy
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}
private const val TAG = "MainActivity/"
private const val SEARCH_API_KEY = BuildConfig.API_KEY
private const val MOVIE_SEARCH_URL =
    "https://api.themoviedb.org/3/movie/popular?&api_key=${SEARCH_API_KEY}"
private const val SHOW_SEARCH_URL =
    "https://api.themoviedb.org/3/tv/top_rated?&api_key=${SEARCH_API_KEY}"

class MainActivity : AppCompatActivity() {
    private lateinit var flixsterRecyclerView: RecyclerView
    private lateinit var showsRecyclerView: RecyclerView
    private val movies = mutableListOf<Flixster>()
    private val shows = mutableListOf<Shows>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContentView(R.layout.activity_main)

        showsRecyclerView = findViewById(R.id.list_shows)

        // TODO: Set up ShowsAdapter with shows
        val showsAdapter = ShowsRecyclerViewAdapter(this, shows)
        showsRecyclerView.adapter = showsAdapter

        showsRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
            .also {
                val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
                showsRecyclerView.addItemDecoration(dividerItemDecoration)
            }
//
        val client1 = AsyncHttpClient()

        client1.get(SHOW_SEARCH_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch shows: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched shows: $json")
                try {
                    // TODO: Create the parsedJSON
                    val parsedJson= json.jsonObject.getJSONArray("results")
                    println("Shows: " + parsedJson)
////                    // TODO: Save the shows
//
                    for (i in 0 until parsedJson.length()) {
                        val showJson = parsedJson.getJSONObject(i)
//                        // Assuming your Flixster data class has properties like title, overview, posterPath
                        val name = showJson.getString("name")
                        val overview = showJson.getString("overview")
                        val posterPath = showJson.getString("poster_path")
//
                        val show = Shows() // Create Shows object
                        show.name = name
                        show.overview = overview
                        show.poster_path = posterPath
                        shows.add(show)
                    }
//                    // Reload the screen
                    showsAdapter.notifyDataSetChanged()
                }
                catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }
        })



//-----------------------------------------------------------------------------------------


        flixsterRecyclerView = findViewById(R.id.list)


        // TODO: Set up ShowsAdapter with movies
        val flixsterAdapter = FlixsterRecyclerViewAdapter(this, movies)
        flixsterRecyclerView.adapter = flixsterAdapter

        flixsterRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
            .also {
                val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
                flixsterRecyclerView.addItemDecoration(dividerItemDecoration)
            }
//
        val client = AsyncHttpClient()

        client.get(MOVIE_SEARCH_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch movies: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched movies: $json")
                try {
                    // TODO: Create the parsedJSON
                    val parsedJson= json.jsonObject.getJSONArray("results")
                    println(parsedJson)
//                    // TODO: Save the articles

                    for (i in 0 until parsedJson.length()) {
                        val movieJson = parsedJson.getJSONObject(i)
                        // Assuming your Flixster data class has properties like title, overview, posterPath
                        val title = movieJson.getString("title")
                        val overview = movieJson.getString("overview")
                        val posterPath = movieJson.getString("poster_path")

                        val movie = Flixster() // Create Flixster object
                        movie.title = title
                        movie.overview = overview
                        movie.poster_path = posterPath
                        movies.add(movie)
                    }
                        // Reload the screen
                        flixsterAdapter.notifyDataSetChanged()
                    }
                catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }
        })
    }
}
