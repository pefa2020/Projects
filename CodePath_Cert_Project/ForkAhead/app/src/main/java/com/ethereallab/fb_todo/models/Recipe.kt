package com.ethereallab.fb_todo.models

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Recipe(
    @SerialName("title")
    val title: String ? = null,
    @SerialName("instructions")
    val instructions: String ? = null,
    @SerialName("ingredients")
    val ingredients: MutableList<String> ? = null,
    @SerialName("poster_path")
    val poster_path: String ? = null,
    @SerialName("recipe_id")
    val recipe_id: String ?= null
) : java.io.Serializable