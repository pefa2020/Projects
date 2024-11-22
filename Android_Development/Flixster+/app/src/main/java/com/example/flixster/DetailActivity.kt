package com.example.flixster

import android.media.Image
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat.getSerializableExtra
import com.bumptech.glide.Glide
import com.codepath.bestsellerlistapp.ARTICLE_EXTRA
import com.codepath.bestsellerlistapp.Flixster
import com.codepath.bestsellerlistapp.SHOW_EXTRA
import com.codepath.bestsellerlistapp.Shows

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var abstractTextView: TextView

    private lateinit var showImage: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var overviewTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        /* FOR MOVIES */
//        // TODO: Find the views for the screen
        mediaImageView = findViewById(R.id.mediaImage)
        titleTextView = findViewById(R.id.mediaTitle)
        abstractTextView = findViewById(R.id.mediaAbstract)
//        // TODO: Get the extra from the Intent
        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(ARTICLE_EXTRA, Flixster::class.java) as? Flixster

        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(ARTICLE_EXTRA) as? Flixster


        }

        // TODO: Set the title, byline, and abstract information from the article
        if (movie != null) {
            titleTextView.text = movie.title
            abstractTextView.text = movie.overview
        }
        // TODO: Load the media image
        if (movie != null) {
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500/"+movie.poster_path)
                .into(mediaImageView)
        }

        //-------------------------------------------------------------------------
        /* FOR SHOWS */

        showImage = findViewById(R.id.mediaImage)
        nameTextView = findViewById(R.id.mediaTitle)
        overviewTextView = findViewById(R.id.mediaAbstract)

        val show = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(SHOW_EXTRA, Flixster::class.java) as? Shows

        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(SHOW_EXTRA) as? Shows
        }

        // TODO: Set the title, byline, and abstract information from the show
        if (show != null) {
            titleTextView.text = show.name
            abstractTextView.text = show.overview
        }
        // TODO: Load the media image
        if (show != null) {
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500/"+show.poster_path)
                .into(mediaImageView)
        }


    }
}