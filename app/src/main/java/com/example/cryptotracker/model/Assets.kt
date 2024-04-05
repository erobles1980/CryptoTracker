package com.example.cryptotracker.model

data class Assets(
    val id: String,
    val name: String,
    val symbol: String,
    val price: Double,
    val percentage: Double
)
