package com.belyaninrom.network

import android.util.Log
import com.belyaninrom.network.di.NetworkComponent
import com.belyaninrom.network.model.Currency
import com.belyaninrom.network.model.NetworkResult
import com.belyaninrom.network.model.Result
import com.google.gson.Gson
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Named

class NetworkMoexImpl @Inject constructor(): NetworkMoex {

    @Inject
    lateinit var restService: MoexRestService

    init {
        NetworkComponent.create().inject(this)
    }

    override suspend fun currentPrice(): NetworkResult<List<Currency>> {
        val response = restService.getCurrentPrice()
        return when (response) {
             is NetworkResult.Success ->
                NetworkResult.Success<List<Currency>>(parseListCurrency(response.data))
            is NetworkResult.Error -> response
            is NetworkResult.Exception -> response
        }
    }

    override suspend fun getPriceWithTime(
        secId: String,
        from: String,
        till: String,
        size: Int
    ): NetworkResult<List<Currency>> {
        val response = restService.getPriceWithTime(secId, from, till, size)
        return when (response) {
            is NetworkResult.Success ->
                NetworkResult.Success<List<Currency>>(parseListCurrency(response.data))
            is NetworkResult.Error -> response
            is NetworkResult.Exception -> response
        }
    }

    private fun parseListCurrency(response: Any?): List<Currency> {
        val json = Gson().toJson(response)
        val jsonObject = JSONObject(json)
        val securities = jsonObject.getJSONObject("securities")
        val data = securities.getJSONArray("data")
        val list = ArrayList<Currency>()

        for (i in 0 until data.length()) {
            val jsonCurrency = data.getJSONArray(i)
            val tradeDate = jsonCurrency.getString(0)
            val tradeTime = jsonCurrency.getString(1)
            val secId = jsonCurrency.getString(2)
            val rate = jsonCurrency.getDouble(3)

            val currency = Currency(tradeDate, tradeTime, secId, rate)
            list.add(currency)
        }
        return list.toList()
    }
}