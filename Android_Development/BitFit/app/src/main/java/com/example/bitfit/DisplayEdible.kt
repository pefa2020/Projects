package com.example.bitfit

data class DisplayEdible(
    val id: Long, // added this
    val name: String?,
    val calories: Int?,
    val mediaImageUrl: String?
) : java.io.Serializable