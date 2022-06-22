package com.belyaninrom.home.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.belyaninrom.home.databinding.VhCurrencyItemBinding
import com.belyaninrom.network.model.Currency

class CurrencyVH(view: View): RecyclerView.ViewHolder(view) {

    val binding by viewBinding(VhCurrencyItemBinding::bind)

    fun bind (currency: Currency, listener: (currency: Currency) -> Unit) {
        binding.rootLL.setOnClickListener {
            listener(currency)
        }
        binding.rateTV.text = currency.rate.toString()
        val symbol = when(currency.secId) {
            "CAD/RUB" -> "$ "
            "CHF/RUB" -> ""
            "GBP/RUB" -> "£ "
            "JPY/RUB" -> "¥ "
            "TRY/RUB" -> ""
            "USD/RUB" -> "$ "
            else -> ""
        }
        val text = symbol + currency.secId
        binding.secIdTV.text = text
    }
}