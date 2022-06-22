package com.belyaninrom.core_api.dto

import com.belyaninrom.network.model.CurrencyNetwork

fun CurrencyNetwork.toCurrencyDb() = CurrencyDb(
    this.tradeDate, this.tradeTime, this.secId,
    this.rate
)

fun CurrencyDb.toCurrencyView() = CurrencyView(
    this.tradeDate, this.tradeTime, this.secId,
    this.rate
)

fun CurrencyNetwork.toCurrencyView() = CurrencyView(
    this.tradeDate, this.tradeTime, this.secId,
    this.rate
)