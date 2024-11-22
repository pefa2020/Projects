package com.codepath.bestsellerlistapp

import android.os.Parcelable
import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The Model for storing a single book from the NY Times API
 *
 * SerializedName tags MUST match the JSON response for the
 * object to correctly parse with the gson library.
 */

@Keep
@Serializable
data class Flixster(
    @SerialName("title")
    var title: String? = null,
    @SerialName("overview")
    var overview: String? = null,
    @SerialName("poster_path")
    var poster_path: String? = null
) : java.io.Serializable