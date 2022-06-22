package com.belyaninrom.network


import com.belyaninrom.network.model.CurrencyNetwork
import com.belyaninrom.network.model.NetworkResult

interface NetworkMoex {

    suspend fun currentPrice(): NetworkResult<List<CurrencyNetwork>>

    suspend fun getPriceWithTime(
        secId: String,
        from: String,
        till: String,
        size: Int
    ): NetworkResult<List<CurrencyNetwork>>
}