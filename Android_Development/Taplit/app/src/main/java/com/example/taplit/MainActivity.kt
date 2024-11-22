package com.example.taplit

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlin.random.Random
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // each activity will have its own xml
        setContentView(R.layout.activity_main)

        var taps = 0

        val button = findViewById<Button>(R.id.b1)
        val textView = findViewById<TextView>(R.id.textView)
        val upgradeButton = findViewById<Button>(R.id.upgradeBtn)

        button.setOnClickListener { v->
            taps++
            textView.text = taps.toString()

            if (taps >= 100) {
                upgradeButton.visibility = View.VISIBLE
                upgradeButton.setOnClickListener {
//                    button.text = "Add 2"

                    button.setOnClickListener {
                        taps += 2
                        textView.text = taps.toString()
                    }
                    // Now we change the icon to light since we are over 100 taps
                    button.background = getDrawable(R.drawable.light)
                    upgradeButton.visibility = View.INVISIBLE
                }
            }
//            var r = Random.nextInt(0,100)
//            if (r == 33)
//                    taps = 0
//            textView.text = "Taps $taps"
        }
//        var i = 0;
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            // find the button we just created
////            findViewById<Button>(R.id.b1).setOnClickListener({v->
////                i++
////                findViewById<TextView>(R.id.textView).setText("Tapped: $i")
////            })
//            insets
//        }
    }
}