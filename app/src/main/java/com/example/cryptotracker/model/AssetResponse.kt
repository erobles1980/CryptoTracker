package com.example.cryptotracker.model

data class AssetResponse (
    val id: String,
    val name: String,
    val symbol: String,
    val priceUsd: String,
    val changePercent24Hr: String?
)