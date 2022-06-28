package com.belyaninrom.home

import android.util.Log
import androidx.lifecycle.*
import com.belyaninrom.core_api.database.CurrenciesDatabaseContract
import com.belyaninrom.uicore.model.CurrencyView
import com.belyaninrom.core_api.dto.toCurrencyDb
import com.belyaninrom.core_api.dto.toCurrencyView
import com.belyaninrom.network.NetworkMoex
import com.belyaninrom.core_api.model.Result
import com.belyaninrom.network.model.NetworkResult
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel
constructor(
    private val networkMoex: NetworkMoex,
    private val database: CurrenciesDatabaseContract
) : ViewModel() {

    private val _currenciesState = MutableStateFlow<Result<List<CurrencyView>>>(
        Result.StartLoading
    )
    val currenciesState: StateFlow<Result<List<CurrencyView>>> = _currenciesState

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        _currenciesState.value = Result.Error(throwable)
    }

    fun getListCurrency() {
        viewModelScope.launch(exceptionHandler) {
            _currenciesState.value = Result.StartLoading
            val response = networkMoex.currentPrice()
            Log.d("HomeViewModel", "response $response")
            when (response) {
                is NetworkResult.Success -> {
                    database.currencyDao().setCurrencies(response.data.map { it.toCurrencyDb() })
                    _currenciesState.value =
                        Result.Success(response.data.map { it.toCurrencyView() })
                }
                is NetworkResult.Error -> {
                    _currenciesState.value = Result.Success(
                        database.currencyDao().getCurrencies().map { it.toCurrencyView() })
                    _currenciesState.value =
                        Result.Error(Exception("${response.code} ${response.message}"))
                }
                is NetworkResult.Exception -> {
                    _currenciesState.value = Result.Success(
                        database.currencyDao().getCurrencies().map { it.toCurrencyView() })
                    _currenciesState.value = Result.Error(response.e)
                }
            }
        }
    }
}

class HomeViewModelFactory @Inject constructor(
    val networkMoex: NetworkMoex,
    val database: CurrenciesDatabaseContract
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(networkMoex, database) as T
    }
}