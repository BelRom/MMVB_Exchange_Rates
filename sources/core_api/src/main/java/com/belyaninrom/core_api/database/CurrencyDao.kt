package com.belyaninrom.core_api.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.belyaninrom.core_api.dto.CurrencyDb

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM CURRENCIES")
    suspend fun getCurrencies(): List<CurrencyDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setCurrencies(currencies: List<CurrencyDb>)
}