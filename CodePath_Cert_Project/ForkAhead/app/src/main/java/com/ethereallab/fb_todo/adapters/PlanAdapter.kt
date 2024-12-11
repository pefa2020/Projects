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
import com.ethereallab.fb_todo.fragments.MealsFragment
import com.ethereallab.fb_todo.models.Recipe



class PlanAdapter(
    private val context: Context,
    private val mealsFragment_context: MealsFragment,
    private val plan_recipes: List<Recipe>,// Click listener as a lambda
) : RecyclerView.Adapter<PlanAdapter.PlanRecipesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanRecipesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_plan_recipe, parent, false)
        return PlanRecipesViewHolder(view)
    }

    /**
     * This inner class lets us refer to all the different View elements
     * (Yes, the same ones as in the XML layout files!)
     */
    inner class PlanRecipesViewHolder(mView: View) : RecyclerView.ViewHolder(mView),
        View.OnClickListener {
        var mItem: Recipe? = null
        val mMealTitle: TextView = mView.findViewById<View>(R.id.recipe_plan_title) as TextView
        //        val mOverview : TextView = mView.findViewById<View>(id.movie_overview) as TextView
        val mMealImage: ImageView = mView.findViewById<View>(R.id.recipe_plan_image) as ImageView

        init {
            itemView.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            // TODO: Get selected article
            val plan_recipe = plan_recipes[adapterPosition]
            println("Recipe: " + plan_recipe)
//
//            // TODO: Navigate to Details screen and pass selected article
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(RECIPE_EXTRA, plan_recipe)
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
    override fun onBindViewHolder(holder: PlanRecipesViewHolder, position: Int) {
        val plan_recipe = plan_recipes[position]

        holder.mItem = plan_recipe
        holder.mMealTitle.text = plan_recipe.title
//        holder.mOverview.text = movie.overview

        Glide.with(holder.mMealImage)
            .load(plan_recipe.poster_path)
            .centerInside()
            .transform(RoundedCorners(20))
            .into(holder.mMealImage)

    }

    /**
     * Remember: RecyclerView adapters require a getItemCount() method.
     */
    override fun getItemCount(): Int {
        return plan_recipes.size
    }
}
