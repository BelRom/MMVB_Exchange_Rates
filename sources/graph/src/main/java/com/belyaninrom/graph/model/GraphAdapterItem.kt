package com.belyaninrom.graph.model

import java.util.*

sealed interface GraphAdapterItem {
    class Chart : GraphAdapterItem
    class Currency (
        tradeDate: String,
        tradeTime: String,
        secId: String,
        rate: Double
    ) : com.belyaninrom.uicore.model.CurrencyView (tradeDate, tradeTime, secId, rate), GraphAdapterItem
    class SelectedDate (var firstDate: Date?, var lastDate: Date?) : GraphAdapterItem
}