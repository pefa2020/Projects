package com.example.bitfit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.launch
import androidx.compose.ui.semantics.text
import androidx.lifecycle.lifecycleScope
import com.example.bitfit.databinding.ActivityMainBinding
import com.example.bitfit.databinding.FragmentDashboardBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.min

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        val edibleApplication = requireActivity().application as EdibleApplication
        val view = binding.root

//        val avg_calories = edibleApplication.db.edibleDao().getAvgCalories()
//        val min_calories = edibleApplication.db.edibleDao().getMinCalories()
//        val max_calories = edibleApplication.db.edibleDao().getMaxCalories()
//
//        val avgCaloriesTextView = view.findViewById<TextView>(R.id.avg_calories)
//        val minCaloriesTextView = view.findViewById<TextView>(R.id.min_calories)
//        val maxCaloriesTextView = view.findViewById<TextView>(R.id.max_calories)
//        avgCaloriesTextView?.text = avg_calories.toString()
//        minCaloriesTextView?.text = min_calories.toString()
//        maxCaloriesTextView?.text = max_calories.toString()

//        lifecycleScope.launch {
//            edibleApplication.db.edibleDao().getAll().collect { databaseList -> // (application as EdibleApplication)
//                updateEdibles(databaseList)
//            }
//        }

        lifecycleScope.launch(Dispatchers.IO) {
            val avgCalories = edibleApplication.db.edibleDao().getAvgCalories()
            val minCalories = edibleApplication.db.edibleDao().getMinCalories()
            val maxCalories = edibleApplication.db.edibleDao().getMaxCalories()

            // Update UI on the main thread
                view.findViewById<TextView>(R.id.avg_calories)?.text = avgCalories.toString()
                view.findViewById<TextView>(R.id.min_calories)?.text = minCalories.toString()
                view.findViewById<TextView>(R.id.max_calories)?.text = maxCalories.toString()
        }



        return view// inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    companion object {
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }
}