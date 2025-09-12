package com.unicorndevelopers.inkspiration.repository

import android.util.Log
import com.unicorndevelopers.inkspiration.utils.RetrofitInstance
import java.net.UnknownHostException

class QuoteRepository {

    private val quoteService = RetrofitInstance.quoteService
    private val imageService = RetrofitInstance.imageService

    suspend fun getQuote(limit: Int): ArrayList<String>? {
        try {
            val response = quoteService.getQuote(limit)
            return if (response.isSuccessful && response.body() != null) {
                val quoteList = ArrayList<String>()
                response.body()?.data?.forEach {
                    quoteList.add(it.quote)
                }
                return quoteList
            } else {
                null
            }
        } catch (ex: UnknownHostException) {
            Log.d("API Call", "Error: ${ex.message}")
            return ArrayList()
        } catch (ex: Exception) {
            Log.d("API Call", "Error: ${ex.message}")
            return null
        }
    }

    suspend fun prefetchQuote(): String? {
        try {
            val response = quoteService.getQuote(1)
            return if (response.isSuccessful && response.body() != null) {
                return response.body()!!.data[0].quote
            } else {
                null
            }
        } catch (ex: UnknownHostException) {
            Log.d("API Call", "Error: ${ex.message}")
            return ""
        } catch (ex: Exception) {
            Log.d("API Call", "Error: ${ex.message}")
            return null
        }
    }

    suspend fun getCategoryImage(category: String, count: Int): ArrayList<String?> {
        val url = ArrayList<String?>()
        for (i in 1..count) {
            try {
                val response = imageService.getCategoryImage(1)
                if (response.isSuccessful && response.body() != null) {
                    url.add(response.body()?.photos[0]?.url)
                }
            } catch (ex: Exception) {
                Log.d("API Call", "getCategoryImage: ${ex.message}")
            }
        }
        return url
    }

    suspend fun prefetchCategoryImage(): String? {
        var url: String? = null
        try {
            val response = imageService.getCategoryImage(1)
            if (response.isSuccessful && response.body() != null) {
                url = response.body()?.photos[0]?.url
            }
        } catch (ex: Exception) {
            Log.d("API Call", "getCategoryImage: ${ex.message}")
        }
        return url
    }
}