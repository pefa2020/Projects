package com.codepath.bestsellerlistapp

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Movie
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.flixster.DetailActivity
import com.example.flixster.R.id
import com.example.flixster.OnListFragmentInteractionListener
import com.example.flixster.R
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer


/**
 * [RecyclerView.Adapter] that can display a [BestSellerBook] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */

const val SHOW_EXTRA = "SHOW_EXTRA"

class ShowsRecyclerViewAdapter(
    private val context: Context,
    private val shows: List<Shows>
//    private val mListener: OnListFragmentInteractionListener?
)
    : RecyclerView.Adapter<ShowsRecyclerViewAdapter.ShowsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_shows, parent, false)
        return ShowsViewHolder(view)
    }

    /**
     * This inner class lets us refer to all the different View elements
     * (Yes, the same ones as in the XML layout files!)
     */
    inner class ShowsViewHolder(mView: View) : RecyclerView.ViewHolder(mView),
        View.OnClickListener {
        var mItem: Shows? = null
        val mMovieTitle: TextView = mView.findViewById<View>(id.show_name) as TextView
        //        val mOverview : TextView = mView.findViewById<View>(id.movie_overview) as TextView
        val mMovieImage: ImageView = mView.findViewById<View>(id.show_image) as ImageView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            // TODO: Get selected article
//            val article = movies[absoluteAdapterPosition]
            val show = shows[adapterPosition]
            println("Movie: " + show)
//
//            // TODO: Navigate to Details screen and pass selected article
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(SHOW_EXTRA, show)
            val options = ActivityOptionsCompat.makeCustomAnimation(
                context,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            context.startActivity(intent, options.toBundle())
        }

    }

    /**
     * This lets us "bind" each Views in the ViewHolder to its' actual data!
     */
    override fun onBindViewHolder(holder: ShowsViewHolder, position: Int) {
        val show = shows[position]

        holder.mItem = show
        holder.mMovieTitle.text = show.name
//        holder.mOverview.text = movie.overview

        Glide.with(holder.mMovieImage)
            .load("https://image.tmdb.org/t/p/w500/"+show.poster_path)
            .centerInside()
            .transform(RoundedCorners(20))
            .into(holder.mMovieImage)

    }

    /**
     * Remember: RecyclerView adapters require a getItemCount() method.
     */
    override fun getItemCount(): Int {
        return shows.size
    }
}