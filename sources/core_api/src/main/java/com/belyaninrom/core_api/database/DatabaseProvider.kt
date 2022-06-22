package com.belyaninrom.core_api.database

interface DatabaseProvider {

    fun provideDatabase(): CurrenciesDatabaseContract

    fun currenciesDao(): CurrencyDao
}