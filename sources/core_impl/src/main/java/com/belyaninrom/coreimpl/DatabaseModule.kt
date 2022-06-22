package com.belyaninrom.coreimpl

import android.content.Context
import androidx.room.Room
import com.belyaninrom.core_api.database.CurrencyDao
import com.belyaninrom.core_api.database.CurrenciesDatabaseContract
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

private const val CURRENCIES_DATABASE_NAME = "CURRENCIES_DB"

@Module
class DatabaseModule {

    @Provides
    @Reusable
    fun provideHabitsDao(habitsDatabaseContract: CurrenciesDatabaseContract): CurrencyDao {
        return habitsDatabaseContract.currencyDao()
    }

    @Provides
    @Singleton
    fun provideHabitsDatabase(context: Context): CurrenciesDatabaseContract {
        return Room.databaseBuilder(
            context,
            CurrenciesDatabase::class.java, CURRENCIES_DATABASE_NAME
        ).build()
    }
}