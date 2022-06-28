package com.belyaninrom.graph

import android.util.Log
import androidx.lifecycle.*
import com.belyaninrom.core_api.dto.toCurrencyView
import com.belyaninrom.uicore.model.CurrencyView
import com.belyaninrom.core_api.model.Result
import com.belyaninrom.network.NetworkMoex
import com.belyaninrom.network.model.NetworkResult
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel
constructor(
    private val networkMoex: NetworkMoex,
) : ViewModel() {

    private val _graphState = MutableStateFlow<Result<List<CurrencyView>>>(
        Result.StartLoading
    )
    val graphState: StateFlow<Result<List<CurrencyView>>> = _graphState

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        _graphState.value = Result.Error(throwable)
    }

    fun getListCurrency(secId: String, firstDate: String, lastDate: String) {
        viewModelScope.launch(exceptionHandler) {
            _graphState.value = Result.StartLoading
            val response = networkMoex.getPriceWithTime(secId, firstDate, lastDate, 1000)
            Log.d("HomeViewModel", "response $response")
            when (response) {
                is NetworkResult.Success -> {
                    withContext(Dispatchers.Main) {
                        _graphState.value =
                            Result.Success(response.data.map { it.toCurrencyView() })
                    }
                }
                is NetworkResult.Error -> {
                    _graphState.value =
                        Result.Error(Exception("${response.code} ${response.message}"))
                }
                is NetworkResult.Exception -> {
                    _graphState.value = Result.Error(response.e)
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