package com.belyaninrom.home

import android.util.Log
import androidx.lifecycle.*
import com.belyaninrom.network.NetworkMoex
import com.belyaninrom.network.model.Currency
import com.belyaninrom.home.model.Result
import com.belyaninrom.network.model.NetworkResult
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel
constructor(
    private val networkMoex: NetworkMoex
) : ViewModel() {

    private val _currenciesState = MutableStateFlow<Result<List<Currency>>>(
        Result.StartLoading
    )
    val currenciesState: StateFlow<Result<List<Currency>>> = _currenciesState

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
                    _currenciesState.value = Result.Success(response.data)
                }
                is NetworkResult.Error -> {
                    _currenciesState.value = Result.Error(Exception("${response.code} ${response.message}"))
                }
                is NetworkResult.Exception -> {
                    _currenciesState.value = Result.Error(response.e)
                }
            }
        }
    }
}

class HomeViewModelFactory @Inject constructor(
    val networkMoex: NetworkMoex
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(networkMoex) as T
    }
}