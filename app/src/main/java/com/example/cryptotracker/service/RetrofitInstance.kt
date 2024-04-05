package com.example.cryptotracker.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val base_url="https://cc185e84-6947-4ac8-82df-1a065b4230c5.mock.pstmn.io/v2/"

    private val retrofit:Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val assetsService:AssetsService by lazy {
        retrofit.create(AssetsService::class.java)
    }

}