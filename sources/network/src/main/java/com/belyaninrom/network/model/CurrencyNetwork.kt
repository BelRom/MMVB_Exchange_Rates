package com.belyaninrom.network.model

data class CurrencyNetwork(
    val tradeDate: String,
    val tradeTime: String,
    val secId: String,
    val rate: Double
)