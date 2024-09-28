package com.unicorndevelopers.inkspiration.service

import com.unicorndevelopers.inkspiration.models.QuoteData
import com.unicorndevelopers.inkspiration.models.QuoteResponse
import retrofit2.Response
import retrofit2.http.GET

interface QuoteService {

    @GET("api/quotes/?limit=2")
    suspend fun getQuote(): Response<QuoteResponse>
}