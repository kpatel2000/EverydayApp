package com.kp.everdayapp.utils

import com.kp.everdayapp.BuildConfig
import com.kp.everdayapp.service.ImageService
import com.kp.everdayapp.service.QuoteService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://api.api-ninjas.com/v1/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient()
                .newBuilder()
                .addNetworkInterceptor { chain ->
                    val request = chain.request().newBuilder()
                    request.addHeader("x-api-key", BuildConfig.API_KEY)
                    chain.proceed(request.build())
                }
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val retrofitImage: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient()
                .newBuilder()
                .addNetworkInterceptor { chain ->
                    val request = chain.request().newBuilder()
                    request.addHeader("x-api-key", BuildConfig.API_KEY)
                    chain.proceed(request.build())
                }
                .build()
            )
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    val quoteService: QuoteService by lazy {
        retrofit.create(QuoteService::class.java)
    }
    val imageService: ImageService by lazy {
        retrofitImage.create(ImageService::class.java)
    }
}