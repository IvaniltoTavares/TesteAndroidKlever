package com.example.desafioandroidklever.data.remote.model

data class ResultadoBlockchainAPIItem(
    val last_trade_price: Double,
    val price_24h: Double,
    val symbol: String,
    val volume_24h: Double
)