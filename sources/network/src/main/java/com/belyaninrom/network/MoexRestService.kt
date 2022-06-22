package com.belyaninrom.network

import com.belyaninrom.network.model.NetworkResult
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoexRestService {

    @GET("iss/statistics/engines/futures/markets/indicativerates/securities.json")
    suspend fun getCurrentPrice(): NetworkResult<Any>

    @GET("/iss/statistics/engines/futures/markets/indicativerates/securities/{secId}.json")
    suspend fun getPriceWithTime(@Path("secId") secId: String,
                                @Query("from") from: String,
                                @Query("till") till: String,
                                @Query("PAGESIZE") size: Int
    ): NetworkResult<Any>
}