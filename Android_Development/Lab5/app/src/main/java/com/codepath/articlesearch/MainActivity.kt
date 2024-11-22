package com.codepath.articlesearch

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
//import androidx.glance.visibility
//import androidx.compose.ui.semantics.text
//import androidx.glance.visibility
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout // Import SwipeRefreshLayout
import com.codepath.articlesearch.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"
private const val SEARCH_API_KEY = BuildConfig.API_KEY
private const val ARTICLE_SEARCH_URL =
    "https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=${SEARCH_API_KEY}"

class MainActivity : AppCompatActivity() {
    private lateinit var articlesRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private val articles = mutableListOf<DisplayArticle>()
    private val articles2 = mutableListOf<Article>()
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout // Add this line
    private lateinit var  toggle: SwitchCompat
    private lateinit var sharedPref: SharedPreferences
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager
    private lateinit var networkStatusTextView: TextView

    var my_cache = "no"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        if (sharedPref.getString("key", null) != null) {
            // Already declared
            my_cache = sharedPref.getString("key", null)!!
        } else {
            val editor = sharedPref.edit()
            editor.putString("key", "no")
            editor.apply()
            my_cache = sharedPref.getString("key", null)!!
        }
        toggle = findViewById(R.id.switchcompat)
        if (my_cache == "no") {
            toggle.isChecked = false
        } else {
            toggle.isChecked = true
        }

        toggle.setOnCheckedChangeListener { _,
            isChecked ->
            if (isChecked) {
                // Should remember user said yes to save into cache.
                val editor = sharedPref.edit()
                editor.putString("key", "yes")
                editor.apply()
                my_cache = sharedPref.getString("key", null)!!

            } else {
                // It's unchecked. Should remember user said no to save into cache
                val editor = sharedPref.edit()
                editor.putString("key", "no")
                editor.apply()

                my_cache = sharedPref.getString("key", null)!!

            }
        }

        articlesRecyclerView = findViewById(R.id.articles)
        swipeRefreshLayout = findViewById(R.id.swiperefresh) // Initialize SwipeRefreshLayout

        articleAdapter = ArticleAdapter(this, articles)
        articlesRecyclerView.adapter = articleAdapter

        articlesRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            articlesRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        lifecycleScope.launch {
            fetchArticles()
            (application as ArticleApplication).db.articleDao().getAll().collect { databaseList ->
                updateArticles(databaseList)
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            lifecycleScope.launch {
                fetchArticles()
                swipeRefreshLayout.isRefreshing = false
                (application as ArticleApplication).db.articleDao().getAll().collect { databaseList ->
                    updateArticles(databaseList)
                }
            }
        }




        fun isNetworkAvailable(context: Context?): Boolean {
            if (context == null) return false
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                            return true
                        }
                    }
                }
            } else {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    return true
                }
            }
            return false
        }

        networkStatusTextView = findViewById(R.id.networkStatusTextView)
        if (isNetworkAvailable(this)) {
            println("Connected to the internet")

        } else {
            println("Not connected to the internet")

        }
        if (!isNetworkAvailable(this)) {
            binding.root.setBackgroundColor(Color.parseColor("#FFCCCB")) // Light red color
            networkStatusTextView.text = "NO INTERNET, RETRYING..."
            networkStatusTextView.visibility = View.VISIBLE

            lifecycleScope.launch {
                while (!isNetworkAvailable(this@MainActivity)) {
                    delay(5000) // Retry every 5 seconds
                    fetchArticles() // Attempt to fetch articles again
                }

                // Connection established, update UI
                withContext(Dispatchers.Main) {
                    binding.root.setBackgroundColor(Color.WHITE) // Reset background color
                    networkStatusTextView.visibility = View.GONE // Hide the TextView
                }
            }
        }


    }



    override fun onDestroy() {
        super.onDestroy()

        if (my_cache == "no") { // Check if caching is disabled
            println("Destroyed")
            lifecycleScope.launch(IO) {
                (application as ArticleApplication).db.articleDao().deleteAll()
            }
        }
    }


    private suspend fun fetchArticles() {
        val client = AsyncHttpClient()
        client.get(ARTICLE_SEARCH_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch articles: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched articles: $json")
                try {
                    val parsedJson = createJson().decodeFromString(
                        SearchNewsResponse.serializer(),
                        json.jsonObject.toString()
                    )

                    parsedJson.response?.docs?.let { list ->
                        lifecycleScope.launch(IO) {
                            if (my_cache == "yes") {
                                println("My cache is  YES")
                                (application as ArticleApplication).db.articleDao().deleteAll()
                                (application as ArticleApplication).db.articleDao().insertAll(
                                list.map {
                                    ArticleEntity(
                                        headline = it.headline?.main,
                                        articleAbstract = it.abstract,
                                        byline = it.byline?.original,
                                        mediaImageUrl = it.mediaImageUrl
                                    )
                                })
                            } else {
                                // my_cache == "no" => We don't save it into database
                                println("My_cache is NO")
                                (application as ArticleApplication).db.articleDao().deleteAll()
                                withContext(Dispatchers.Main) {
                                    articles.clear()
                                    articles.addAll(list.map { doc ->
                                        DisplayArticle(
                                            doc.headline?.main,
                                            doc.abstract,
                                            doc.byline?.original,
                                            doc.mediaImageUrl
                                        )
                                    })
                                    articleAdapter.notifyDataSetChanged() // Update the adapter with new data
                                }
                            }
                        }
                    }

                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }
        })
    }

    private suspend fun updateArticles(databaseList: List<ArticleEntity>) {
        val mappedList = databaseList.map { entity ->
            DisplayArticle(
                entity.headline,
                entity.articleAbstract,
                entity.byline,
                entity.mediaImageUrl
            )
        }
        withContext(Dispatchers.Main) {
            articles.clear()
            articles.addAll(mappedList)
            articleAdapter.notifyDataSetChanged()
        }
    }
}


//package com.codepath.articlesearch
//
////import ArticleEntity
////import DisplayArticle
//import android.os.Bundle
//import android.util.Log
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.DividerItemDecoration
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.codepath.articlesearch.databinding.ActivityMainBinding
//import com.codepath.asynchttpclient.AsyncHttpClient
//import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
//import kotlinx.coroutines.Dispatchers.IO
//import kotlinx.coroutines.launch
//import kotlinx.serialization.json.Json
//import okhttp3.Headers
//import org.json.JSONException
//
//fun createJson() = Json {
//    isLenient = true
//    ignoreUnknownKeys = true
//    useAlternativeNames = false
//}
//
//private const val TAG = "MainActivity/"
//private const val SEARCH_API_KEY = BuildConfig.API_KEY
//private const val ARTICLE_SEARCH_URL =
//    "https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=${SEARCH_API_KEY}"
//
//class MainActivity : AppCompatActivity() {
//    private lateinit var articlesRecyclerView: RecyclerView
//    private lateinit var binding: ActivityMainBinding
//    private val articles = mutableListOf<DisplayArticle>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        val view = binding.root
//        setContentView(view)
//
//        articlesRecyclerView = findViewById(R.id.articles)
//        // TODO: Set up ArticleAdapter with articles
//        val articleAdapter = ArticleAdapter(this, articles)
//        articlesRecyclerView.adapter = articleAdapter
//
//        articlesRecyclerView.layoutManager = LinearLayoutManager(this).also {
//            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
//            articlesRecyclerView.addItemDecoration(dividerItemDecoration)
//        }
//
//        val client = AsyncHttpClient()
//        client.get(ARTICLE_SEARCH_URL, object : JsonHttpResponseHandler() {
//            override fun onFailure(
//                statusCode: Int,
//                headers: Headers?,
//                response: String?,
//                throwable: Throwable?
//            ) {
//                Log.e(TAG, "Failed to fetch articles: $statusCode")
//            }
//
//            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
//                Log.i(TAG, "Successfully fetched articles: $json")
//                try {
//                    // TODO: Create the parsedJSON
//                    val parsedJson = createJson().decodeFromString(
//                        SearchNewsResponse.serializer(),
//                        json.jsonObject.toString()
//                    )
//
//                    // TODO: Save the articles
//                    parsedJson.response?.docs?.let { list ->
//                        lifecycleScope.launch(IO) {
//                            (application as ArticleApplication).db.articleDao().deleteAll()
//                            (application as ArticleApplication).db.articleDao().insertAll(list.map {
//                                ArticleEntity(
//                                    headline = it.headline?.main,
//                                    articleAbstract = it.abstract,
//                                    byline = it.byline?.original,
//                                    mediaImageUrl = it.mediaImageUrl
//                                )
//                            })
//                        }
//                    }
//
//                } catch (e: JSONException) {
//                    Log.e(TAG, "Exception: $e")
//                }
//            }
//
//        })
//
//        lifecycleScope.launch {
//            (application as ArticleApplication).db.articleDao().getAll().collect { databaseList ->
//                databaseList.map { entity ->
//                    DisplayArticle(
//                        entity.headline,
//                        entity.articleAbstract,
//                        entity.byline,
//                        entity.mediaImageUrl
//                    )
//                }.also { mappedList ->
//                    articles.clear()
//                    articles.addAll(mappedList)
//                    articleAdapter.notifyDataSetChanged()
//                }
//            }
//        }
//
//    }
//}