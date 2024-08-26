package com.kp.everdayapp.repository

import com.kp.everdayapp.models.QuoteData
import com.kp.everdayapp.utils.RetrofitInstance
import kotlin.text.Typography.quote

class QuoteRepository {

    private val quoteService = RetrofitInstance.quoteService

    suspend fun getQuote(): List<QuoteData>? {
        val response = quoteService.getQuote()
        return if (response.isSuccessful && response.body() != null) {
            val quote = mutableListOf<QuoteData>()
            quote.add(response.body()!![0])
            quote.add(QuoteData("Dummy1", "Dummy Author", ""))
            quote.add(QuoteData("Dummy2", "Dummy Author", ""))
            quote.add(QuoteData("Dummy3", "Dummy Author", ""))
            quote.add(QuoteData("Dummy4", "Dummy Author", ""))
            quote.add(QuoteData("Dummy5", "Dummy Author", ""))
            quote.add(QuoteData("Dummy6", "Dummy Author", ""))
            quote.add(QuoteData("Dummy7", "Dummy Author", ""))
            quote.add(QuoteData("Dummy8", "Dummy Author", ""))
            return quote.toList()
        }else {
            null
        }
    }
}