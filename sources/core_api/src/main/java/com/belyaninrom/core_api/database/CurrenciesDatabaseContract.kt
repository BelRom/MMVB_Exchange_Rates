package com.belyaninrom.core_api.database

import com.belyaninrom.core_api.database.CurrencyDao

interface CurrenciesDatabaseContract {

    fun currencyDao(): CurrencyDao
}