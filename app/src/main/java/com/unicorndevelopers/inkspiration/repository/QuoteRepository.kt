package com.unicorndevelopers.inkspiration.repository

import android.util.Log
import com.unicorndevelopers.inkspiration.models.Quote
import com.unicorndevelopers.inkspiration.models.QuoteData
import com.unicorndevelopers.inkspiration.utils.RetrofitInstance

class QuoteRepository {

    private val quoteService = RetrofitInstance.quoteService
    private val imageService = RetrofitInstance.imageService

    suspend fun getQuote(): Quote? {
        val response = quoteService.getQuote()
        return if (response.isSuccessful && response.body() != null) {
            val quote = mutableListOf<QuoteData>()
            quote.add(response.body()!![0])
            quote.add(QuoteData("", "", ""))
            val url = getCategoryImage(response.body()!![0].category.lowercase())
            return Quote(quote.toList(), url)
        } else {
            null
        }
    }

    suspend fun getCategoryImage(category: String): String? {

        var url: String? = null
        try {
            val response = imageService.getCategoryImage(category)
            if (response.isSuccessful && response.body() != null) {
                url = response.body()
            }
        } catch (ex: Exception) {
            url = null
            Log.d("TAG", "getCategoryImage: ${ex.message}")
        }
        return url
    }
}