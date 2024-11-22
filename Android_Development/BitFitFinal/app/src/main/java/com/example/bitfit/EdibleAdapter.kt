package com.example.bitfit


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
//import androidx.compose.foundation.text2.input.delete
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val EDIBLE_EXTRA = "EDIBLE_EXTRA"
private const val TAG = "ArticleAdapter"


class EdibleAdapter(
    private val context: Context,
    private val edibles: MutableList<DisplayEdible>, // Make edibles mutable
    private val lifecycleScope: LifecycleCoroutineScope, // Add lifecycleScope
    private val onItemLongClicked: (Long) -> Unit
) : RecyclerView.Adapter<EdibleAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.edible_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val edible = edibles[position]
        holder.bind(edible)

        // ... inside onBindViewHolder ...

        holder.itemView.setOnLongClickListener {
//            val edibleToDelete = edibles[holder.adapterPosition]
            println("You clicked on id: " + edible.id)
            onItemLongClicked(edible.id)
            true
        }
    }

    override fun getItemCount() = edibles.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
            private val nameTextView = itemView.findViewById<TextView>(R.id.nameEdible)
        private val caloriesTextView = itemView.findViewById<TextView>(R.id.caloriesEdible)

        init {
            itemView.setOnClickListener(this)
        }

        // TODO: Write a helper method to help set up the onBindViewHolder method
        fun bind(edible: DisplayEdible) {
            nameTextView.text = edible.name
            caloriesTextView.text = edible.calories.toString()

//            Glide.with(context)
//                .load(article.mediaImageUrl)
//                .into(mediaImageView)
        }

        // No need since we are not clicking each of the items (in Recycler View)
        override fun onClick(v: View?) {
            // TODO: Get selected article
            val edible = edibles[absoluteAdapterPosition]

//            // TODO: Navigate to Details screen and pass selected article
//            val intent = Intent(context, DetailActivity::class.java)
//            intent.putExtra(EDIBLE_EXTRA, article)
//            context.startActivity(intent)
        }
    }
}