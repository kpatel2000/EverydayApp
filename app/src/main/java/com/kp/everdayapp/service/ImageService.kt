package com.kp.everdayapp.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageService {

    @GET("randomimage")
    suspend fun getCategoryImage(@Query("category") category: String) : Response<String>
}