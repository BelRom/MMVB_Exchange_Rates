package com.belyaninrom.uicore.model


open class CurrencyView (
    val tradeDate: String,
    val tradeTime: String,
    val secId: String,
    val rate: Double
) {
    var tradeDataDate: Long = 0L
}