package com.example.cryptotracker.data

import com.example.cryptotracker.model.AssetsResponse
import com.example.cryptotracker.service.AssetsService
import com.example.cryptotracker.service.RetrofitInstance

class AssetsRepository {

    private val assetsService=RetrofitInstance.assetsService

    suspend fun getAssets(): AssetsResponse {
        return assetsService.getAssets()
    }

}