package com.unicorndevelopers.inkspiration.repository

import android.util.Log
import com.unicorndevelopers.inkspiration.models.Quote
import com.unicorndevelopers.inkspiration.models.QuoteData
import com.unicorndevelopers.inkspiration.utils.RetrofitInstance
import kotlin.text.Typography.quote

class QuoteRepository {

    private val quoteService = RetrofitInstance.quoteService
    private val imageService = RetrofitInstance.imageService

    suspend fun getQuote(): Quote? {
        try {

            val response = quoteService.getQuote()
            return if (response.isSuccessful && response.body() != null) {
                val url = getCategoryImage("nature")
                return Quote(response.body()?.data, url)
            } else {
                null
            }
        }catch (ex: Exception) {
            Log.d("API Call", "Error: ${ex.message}")
            return null
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
            Log.d("API Call", "getCategoryImage: ${ex.message}")
        }
        return url
    }
}