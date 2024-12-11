package com.ethereallab.fb_todo.fragments

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
//import androidx.compose.animation.with

//import androidx.compose.ui.layout.layout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ethereallab.fb_todo.LoginActivity
import com.ethereallab.fb_todo.R
import com.ethereallab.fb_todo.databinding.FragmentHomeBinding
import com.ethereallab.fb_todo.databinding.FragmentProfileBinding
import com.ethereallab.fb_todo.models.Todo
import com.ethereallab.fb_todo.repository.ToDoRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlin.collections.map
import kotlin.collections.toTypedArray

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val userId = auth.currentUser?.uid
        binding.theUserId.text = userId
        val maskedUserId = if (userId != null && userId.isNotEmpty()) {
            userId.substring(0, 4) + "***" + userId
        } else {
            "User ID not available"
        }

        Glide.with(this)
            .load(R.drawable.avatar) // Replace with your image source
            .apply(RequestOptions.bitmapTransform(RoundedCorners(16))) // Adjust corner radius as needed
            .into(binding.profileImageView)


        binding.logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

        return binding.root

    }
    // added
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
