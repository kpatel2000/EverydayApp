package com.unicorndevelopers.inkspiration.service

import com.unicorndevelopers.inkspiration.models.ImageResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageService {

    @GET("photos/random")
    suspend fun getCategoryImage(@Query("num") num: Int) : Response<ImageResponseModel>
}