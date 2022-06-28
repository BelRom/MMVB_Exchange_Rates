package com.belyaninrom.home.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.belyaninrom.home.databinding.VhHomeCurrencyItemBinding
import com.belyaninrom.uicore.model.CurrencyView

class CurrencyVH(view: View): RecyclerView.ViewHolder(view) {

    val binding by viewBinding(VhHomeCurrencyItemBinding::bind)

    fun bind (currency: CurrencyView, listener: (currency: CurrencyView) -> Unit) {
        binding.rootLL.setOnClickListener {
            listener(currency)
        }
        binding.rateTV.text = currency.rate.toString()
        val symbol = when(currency.secId) {
            "CAD/RUB" -> " $"
            "CHF/RUB" -> ""
            "GBP/RUB" -> " £"
            "JPY/RUB" -> " ¥"
            "TRY/RUB" -> ""
            "USD/RUB" -> " $"
            else -> ""
        }
        val text = currency.secId + symbol
        binding.secIdTV.text = text
    }
}