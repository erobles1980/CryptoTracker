package com.example.cryptotracker.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.cryptotracker.data.AssetsRepository
import com.example.cryptotracker.model.Assets
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AssestsViewModel:ViewModel() {
    private val assetsRepository=AssetsRepository()

    var assets= mutableStateListOf<Assets>()

    fun fetchAssets(){
        viewModelScope.launch {
            try {
                val response=assetsRepository.getAssets().data
                val assetsResponse=response.map{assetsResponse->
//                    val price = assetsResponse.priceUsd.format("%.2f").toDouble()
//                    val percentage=assetsResponse.changePercent24Hr?.format("%.2f")?.toDouble() ?:0.00
                    val roundPrice=String.format("%.2f",assetsResponse.priceUsd.toDouble()).toDouble()
                    //val roundPercentage=String.format("%.2f",assetsResponse.changePercent24Hr?.toDouble()).toDouble() ?: 0.00
                    val roundPercentage=assetsResponse.changePercent24Hr?.toDoubleOrNull()?.let {
                        String.format("%.2f", it).toDouble()
                    } ?: 0.00
                    Assets(
                        assetsResponse.id,
                        assetsResponse.name,
                        assetsResponse.symbol,
//                        price,
//                        percentage
                        roundPrice,
                        roundPercentage
                    )
                }
                assets.addAll(assetsResponse)
            }catch (e:Exception){
                Log.d("FetchAsset: ",e.toString())
            }

        }
    }
}