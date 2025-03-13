package com.unicorndevelopers.inkspiration.service

import com.unicorndevelopers.inkspiration.models.QuoteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteService {

    @GET("api/quotes/")
    suspend fun getQuote(@Query("limit") limit: Int): Response<QuoteResponse>
}