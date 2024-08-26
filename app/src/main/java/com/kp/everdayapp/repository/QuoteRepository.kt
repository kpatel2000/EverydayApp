package com.kp.everdayapp.repository

import com.kp.everdayapp.models.QuoteData
import com.kp.everdayapp.utils.RetrofitInstance

class QuoteRepository {

    private val quoteService = RetrofitInstance.quoteService

    suspend fun getQuote(): QuoteData? {
        val response = quoteService.getQuote()
        return if (response.isSuccessful && response.body() != null) {
            response.body()!![0]
        }else {
            null
        }
    }
}