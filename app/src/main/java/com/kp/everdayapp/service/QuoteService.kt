package com.kp.everdayapp.service

import com.kp.everdayapp.models.QuoteData
import retrofit2.Response
import retrofit2.http.GET

interface QuoteService {

    @GET("quotes")
    suspend fun getQuote(): Response<List<QuoteData>>
}