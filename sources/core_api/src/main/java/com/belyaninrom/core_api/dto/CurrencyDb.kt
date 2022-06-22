package com.belyaninrom.core_api.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CURRENCIES")
data class CurrencyDb (
    val tradeDate: String,
    val tradeTime: String,
    @PrimaryKey
    val secId: String,
    val rate: Double
)