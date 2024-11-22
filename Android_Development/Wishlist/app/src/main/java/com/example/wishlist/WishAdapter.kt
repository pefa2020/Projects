import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.wishlist.R
import kotlin.math.log

class WishAdapter(private val wishes: MutableList<Wish>) : RecyclerView.Adapter<WishAdapter.ViewHolder>() {
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Your holder should contain a member variable for any view that will be set as you render
        // a row
        val nameTextView: TextView
        val priceTextView: TextView
        val linkTextView: TextView

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each sub-view
        init {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            nameTextView = itemView.findViewById(R.id.nameTv)
            priceTextView = itemView.findViewById(R.id.priceTv)
            linkTextView = itemView.findViewById(R.id.linkTv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.wish_item, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Populate data into the item through the holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val wish = wishes.get(position)
        // Set item views based on views and data model
        holder.nameTextView.text = wish.name
        holder.priceTextView.text = wish.price
        holder.linkTextView.text = wish.link
        // Opens link
        holder.linkTextView.setOnClickListener {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(wish.link))
                ContextCompat.startActivity(it.context, browserIntent, null)
                true
            } catch (e: ActivityNotFoundException) {

                Toast.makeText(it.context, "Invalid URL for " + wish.name, Toast.LENGTH_LONG)
                    .show()
                true
            }

        }
        // Hold on object to delete from list
        holder.itemView.setOnLongClickListener {
            println("Long click")
            wishes.removeAt(holder.adapterPosition)
            notifyDataSetChanged()
            true
//            wishes.removeAt(holder.adapterPosition)
//            notifyDataSetChanged()
//            true
        }
    }

    override fun getItemCount(): Int {
        return wishes.size
    }
}

