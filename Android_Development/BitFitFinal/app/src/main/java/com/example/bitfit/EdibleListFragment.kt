package com.example.bitfit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.compose.ui.window.application
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.databinding.ActivityMainBinding
import com.example.bitfit.databinding.FragmentEdibleListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * A simple [Fragment] subclass.
 * Use the [EdibleListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EdibleListFragment : Fragment() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    private lateinit var ediblesRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private lateinit var binding2: FragmentEdibleListBinding
    private val edibles = mutableListOf<DisplayEdible>()
    private lateinit var edibleAdapter: EdibleAdapter
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private suspend fun updateEdibles(databaseList: List<EdibleEntity>) {
        val mappedList = databaseList.map { entity ->
            DisplayEdible(
                entity.id,
                entity.name,
                entity.calories,
                entity.mediaImageUrl
            )
        }
        withContext(Dispatchers.Main) {
            edibles.clear()
            edibles.addAll(mappedList)
            edibleAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_edible_list, container, false)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding2 = FragmentEdibleListBinding.inflate(layoutInflater)
        val edibleApplication = requireActivity().application as EdibleApplication
        val view = binding.root
        val view2 = binding2.root
//        setContentView(view)

        ediblesRecyclerView = view2.findViewById(R.id.ediblesRv) // view

        edibleAdapter = EdibleAdapter(requireContext(), edibles, lifecycleScope) { edibleId ->
            // Handle item long click here
            lifecycleScope.launch(Dispatchers.IO) {
                edibleApplication.db.edibleDao().deleteById(edibleId) // (application as EdibleApplication)
                // ... (Optional) Update UI on main thread ...
            }
        }


        ediblesRecyclerView.adapter = edibleAdapter

        ediblesRecyclerView.layoutManager = LinearLayoutManager(requireContext()).also { // this instead of requireContext()
            val dividerItemDecoration = DividerItemDecoration(requireContext(), it.orientation) // this
            ediblesRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        /*UNCOMMENT */
        submitButton = view2.findViewById(R.id.submit)

        lifecycleScope.launch {
//            fetchEdibles()
            edibleApplication.db.edibleDao().getAll().collect { databaseList -> // (application as EdibleApplication)
                updateEdibles(databaseList)
            }
        }

        println("Reached here")
        /* UNCOMMENT */
        val inputName = view2.findViewById<EditText>(R.id.inputName)
        val inputCalories = view2.findViewById<EditText>(R.id.inputCalories)

        /*UNCOMMENT */
        submitButton.setOnClickListener {
            println("You clicked submit")
            val name = inputName.text.toString()
            val calories = inputCalories.text.toString()

            if (name.isNotEmpty() && calories.isNotEmpty()) {
                // proceed to add to list
                println("Name: $name")
                println("Calories: $calories")
                // Hide virtual keyboard
                inputName.onEditorAction(6)
                inputCalories.onEditorAction(6)

                println("GOod till here")
                lifecycleScope.launch(Dispatchers.IO) {
                    edibleApplication.db //(application as EdibleApplication)
                        .edibleDao().insert(
                            EdibleEntity(name = name, calories = calories.toInt(), mediaImageUrl = "")
                        )

                    withContext(Dispatchers.Main) {
                        inputName.text.clear()
                        inputCalories.text.clear()
                        // ... other UI updates ...
                    }

                }
            }

        }
        return view2
    }

    companion object {
        fun newInstance(): EdibleListFragment {
            return EdibleListFragment()
        }
    }
}