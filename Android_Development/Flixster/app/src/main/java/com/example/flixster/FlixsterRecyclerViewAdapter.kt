package com.codepath.bestsellerlistapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flixster.R.id
import com.example.flixster.OnListFragmentInteractionListener
import com.example.flixster.R

/**
 * [RecyclerView.Adapter] that can display a [BestSellerBook] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class FlixsterRecyclerViewAdapter(
    private val movies: List<Flixster>,
    private val mListener: OnListFragmentInteractionListener?
)
    : RecyclerView.Adapter<FlixsterRecyclerViewAdapter.FlixsterViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlixsterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_flixster, parent, false)
        return FlixsterViewHolder(view)
    }

    /**
     * This inner class lets us refer to all the different View elements
     * (Yes, the same ones as in the XML layout files!)
     */
    inner class FlixsterViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: Flixster? = null
        val mMovieTitle: TextView = mView.findViewById<View>(id.movie_title) as TextView
        val mOverview : TextView = mView.findViewById<View>(id.movie_overview) as TextView
        val mMovieImage: ImageView = mView.findViewById<View>(id.movie_image) as ImageView
    }

    /**
     * This lets us "bind" each Views in the ViewHolder to its' actual data!
     */
    override fun onBindViewHolder(holder: FlixsterViewHolder, position: Int) {
        val movie = movies[position]

        holder.mItem = movie
        holder.mMovieTitle.text = movie.title
        holder.mOverview.text = movie.overview

        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500/"+movie.poster_path)
            .centerInside()
            .into(holder.mMovieImage)

    }

    /**
     * Remember: RecyclerView adapters require a getItemCount() method.
     */
    override fun getItemCount(): Int {
        return movies.size
    }
}