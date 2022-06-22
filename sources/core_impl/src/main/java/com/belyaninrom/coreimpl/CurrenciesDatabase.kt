package com.belyaninrom.coreimpl

import androidx.room.Database
import androidx.room.RoomDatabase
import com.belyaninrom.core_api.database.CurrenciesDatabaseContract
import com.belyaninrom.core_api.dto.CurrencyDb

@Database(entities = [CurrencyDb::class], version = 1)
abstract class CurrenciesDatabase : RoomDatabase(), CurrenciesDatabaseContract