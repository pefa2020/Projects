// TodoAdapter.kt
package com.ethereallab.fb_todo.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.core.app.ActivityOptionsCompat
//import androidx.compose.ui.semantics.text
//import androidx.glance.visibility
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.ethereallab.fb_todo.DetailActivity
import com.ethereallab.fb_todo.R
import com.ethereallab.fb_todo.databinding.ItemTodoBinding
import com.ethereallab.fb_todo.fragments.CreateFragment
import com.ethereallab.fb_todo.models.Recipe

const val RECIPE_EXTRA = "RECIPE_EXTRA"

class RecipeAdapter(
    private val context: Context,
    private val createFragment_context: CreateFragment,
    private val recipes: List<Recipe>,// Click listener as a lambda
    private val cart: MutableList<Recipe>
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    /**
     * This inner class lets us refer to all the different View elements
     * (Yes, the same ones as in the XML layout files!)
     */
    inner class RecipeViewHolder(mView: View) : RecyclerView.ViewHolder(mView),
        View.OnClickListener {
        var mItem: Recipe? = null
        val mMealTitle: TextView = mView.findViewById<View>(R.id.recipe_title) as TextView
        //        val mOverview : TextView = mView.findViewById<View>(id.movie_overview) as TextView
        val mMealImage: ImageView = mView.findViewById<View>(R.id.recipe_image) as ImageView
        val mAddToCartButton: ImageButton = mView.findViewById<ImageButton>(R.id.addToCartButton) as ImageButton

        init {
            itemView.setOnClickListener(this)
//            var bool = 0
            mAddToCartButton.setOnClickListener {
                val recipe = recipes[adapterPosition]

//                if ((bool) == 0 ) {
//                    Toast.makeText(context,"Added to Cart: {${recipe.recipe_id}}", Toast.LENGTH_SHORT).show()
//                    cart.add(recipe)
//                    bool = 1
//                } else {
//                    Toast.makeText(context,"Removed from Cart: {${recipe.recipe_id}}", Toast.LENGTH_SHORT).show()
//                    cart.removeAll{it.recipe_id == recipe.recipe_id}
//                    bool = 0
//                }
                // Only add id: we are requested to, recipe has not already been added, and cart size is <= 10
                if (  !cart.any { it.recipe_id == recipe.recipe_id } && cart.size == 10) { //bool == 0
                    // Don't remove nor add
                    (context as? Activity)?.runOnUiThread {
                        Toast.makeText(context, "Max amount reached for plan (10)", Toast.LENGTH_SHORT).show()
                    }
                }
                else if ( !cart.any { it.recipe_id == recipe.recipe_id } && cart.size < 10) { //bool == 0 &&
                    cart.add(recipe)
                    val a = (mutableListOf <String>())
                    for (i in cart) {
                        i.recipe_id?.let { it1 -> a.add(it1) }
                    }
                    println("Current cart after adding: " + a)
                    // Toast.makeText(context, "Added to Cart: {${recipe.recipe_id}}", Toast.LENGTH_SHORT).show()
                    (context as? Activity)?.runOnUiThread {
                        Toast.makeText(context, "Added to Cart: {${recipe.recipe_id}}", Toast.LENGTH_SHORT).show()
                    }
//                    bool = 1
                    createFragment_context.cartSizeFlow.value = cart.size // Update the flow
                }

                else {
                    cart.removeAll { it.recipe_id == recipe.recipe_id }
                    val a = (mutableListOf <String>())
                    for (i in cart) {
                        i.recipe_id?.let { it1 -> a.add(it1) }
                    }
                    println("Current cart after deletion: "+a)
                    //Toast.makeText(context, "Removed from Cart: {${recipe.recipe_id}}", Toast.LENGTH_SHORT).show()
                    (context as? Activity)?.runOnUiThread {
                        Toast.makeText(context, "Removed from Cart: {${recipe.recipe_id}}", Toast.LENGTH_SHORT).show()
                    }
//                    bool = 0
                    createFragment_context.cartSizeFlow.value = cart.size // Update the flow
                }

            }
        }


        override fun onClick(v: View?) {
            // TODO: Get selected article
            val recipe = recipes[adapterPosition]
            println("Recipe: " + recipe)
//
//            // TODO: Navigate to Details screen and pass selected article
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(RECIPE_EXTRA, recipe)
            context.startActivity(intent)
//            val options = ActivityOptionsCompat.makeCustomAnimation(
//                context,
//                R.anim.slide_in_right,
//                R.anim.slide_out_left
//            )
//            context.startActivity(intent, options.toBundle())
        }

    }

    /**
     * This lets us "bind" each Views in the ViewHolder to its' actual data!
     */
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]

        holder.mItem = recipe
        holder.mMealTitle.text = recipe.title
//        holder.mOverview.text = movie.overview

        Glide.with(holder.mMealImage)
            .load(recipe.poster_path)
            .centerInside()
            .transform(RoundedCorners(20))
            .into(holder.mMealImage)



    }

    /**
     * Remember: RecyclerView adapters require a getItemCount() method.
     */
    override fun getItemCount(): Int {
        return recipes.size
    }
}
