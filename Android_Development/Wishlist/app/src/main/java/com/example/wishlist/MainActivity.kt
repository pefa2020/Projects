package com.example.wishlist

import Wish
import WishFetcher
import WishAdapter
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var wishes: MutableList<Wish>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        wishes = mutableListOf<Wish>()
        val button = findViewById<Button>(R.id.submit)
        val inputName = findViewById<EditText>(R.id.inputName)
        val inputPrice = findViewById<EditText>(R.id.inputPrice)
        val inputLink = findViewById<EditText>(R.id.inputLink)

        // Lookup the RecyclerView in activity layout
        val wishesRv = findViewById<RecyclerView>(R.id.wishesRv)
        // Fetch the list of emails
        wishes = WishFetcher.getWishes()

        // Create adapter passing in the list of emails
        val adapter = WishAdapter(wishes)
//        // Attach the adapter to the RecyclerView to populate items
        wishesRv.adapter = adapter
//        // Set layout manager to position the items
        wishesRv.layoutManager = LinearLayoutManager(this)


        button.setOnClickListener {
            val name = inputName.text.toString()
            val price = inputPrice.text.toString()
            val link = inputLink.text.toString()

            if (name.isNotEmpty() && price.isNotEmpty() && link.isNotEmpty()) {
                // proceed to add to list
                println("Name: $name")
                println("Price: $price")
                println("Link: $link")
                // Hide virtual keyboard
                inputName.onEditorAction(6)
                inputPrice.onEditorAction(6)
                inputLink.onEditorAction(6)

                (wishes as MutableList<Wish>).add(Wish(name, price, link))
                adapter.notifyDataSetChanged()
                inputName.text.clear()
                inputPrice.text.clear()
                inputLink.text.clear()
            } else {
                // do nothing
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}