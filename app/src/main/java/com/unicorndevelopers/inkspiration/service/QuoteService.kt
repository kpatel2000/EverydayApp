package com.unicorndevelopers.inkspiration.service

import com.unicorndevelopers.inkspiration.models.QuoteData
import retrofit2.Response
import retrofit2.http.GET

interface QuoteService {

    @GET("quotes")
    suspend fun getQuote(): Response<List<QuoteData>>
}