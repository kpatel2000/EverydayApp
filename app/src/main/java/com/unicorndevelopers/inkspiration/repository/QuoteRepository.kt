package com.unicorndevelopers.inkspiration.repository

import android.util.Log
import com.unicorndevelopers.inkspiration.models.Quote
import com.unicorndevelopers.inkspiration.models.QuoteData
import com.unicorndevelopers.inkspiration.utils.RetrofitInstance
import java.net.UnknownHostException
import java.util.LinkedList
import java.util.Queue
import kotlin.text.Typography.quote

class QuoteRepository {

    private val quoteService = RetrofitInstance.quoteService
    private val imageService = RetrofitInstance.imageService

    suspend fun getQuote(quote: Quote?): Quote? {
        try {
            val response = quoteService.getQuote()
            return if (response.isSuccessful && response.body() != null) {
                var _quote = quote
                if (_quote != null) {
                    val url = getCategoryImage("nature")
                    _quote.imageUrl?.add(url)
                    _quote.quote?.add(response.body()!!.data[0])
                } else {
                    _quote = Quote(
                        quote = LinkedList<QuoteData>(),
                        imageUrl = LinkedList<String?>(),
                        networkIssue = false
                    )
                    for (i in 0..2) {
                        val url = getCategoryImage("nature")
                        _quote.imageUrl?.add(url)
                        _quote.quote?.add(response.body()!!.data[i])
                    }
                }
//                Log.d("API Call", "Success: ${response.body()} \n $imageUrlList")
                return _quote
            } else {
                null
            }
        }catch (ex: UnknownHostException){
            Log.d("API Call", "Error: ${ex.message}")
            return Quote(quote = null, imageUrl = null, networkIssue = true)
        }
        catch (ex: Exception) {
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