package com.unicorndevelopers.inkspiration.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class QuoteData(
    @SerializedName("quote")
    @Expose
    val quote: String,
    @SerializedName("author")
    @Expose
    val author: String,
    @SerializedName("category")
    @Expose
    val category: String
)

data class Quote(
    val quote: List<QuoteData>?,
    val imageUrl: String?
)
