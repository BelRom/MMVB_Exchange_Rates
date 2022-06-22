package com.belyaninrom.network


import com.belyaninrom.network.model.Currency
import com.belyaninrom.network.model.NetworkResult
import com.belyaninrom.network.model.Result
import javax.inject.Named

interface NetworkMoex {

    suspend fun currentPrice(): NetworkResult<List<Currency>>

    suspend fun getPriceWithTime(
        secId: String,
        from: String,
        till: String,
        size: Int
    ): NetworkResult<List<Currency>>
}