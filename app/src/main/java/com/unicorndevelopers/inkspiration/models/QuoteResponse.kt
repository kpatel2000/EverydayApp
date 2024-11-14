package com.unicorndevelopers.inkspiration.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.Queue


data class QuoteResponse(
    @SerializedName("status")
    @Expose
    val status: Boolean,
    @SerializedName("data")
    @Expose
    val data: List<QuoteData>,
    @SerializedName("total")
    @Expose
    val total: Int
)

data class QuoteData(
    @SerializedName("quote")
    @Expose
    val quote: String,
    @SerializedName("category")
    @Expose
    val category: String
)

data class Quote(
    val quote: Queue<QuoteData>?,
    val imageUrl: Queue<String?>?,
    val networkIssue: Boolean = true
)
