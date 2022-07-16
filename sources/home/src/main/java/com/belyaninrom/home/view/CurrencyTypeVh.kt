package com.belyaninrom.home.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.belyaninrom.home.databinding.VhHomeCurrencyItemBinding
import com.belyaninrom.home.databinding.VhHomeDateItemBinding
import com.belyaninrom.uicore.model.CurrencyView
import java.text.SimpleDateFormat

sealed class CurrencyTypeVh(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class CurrencyVH(private val binding: VhHomeCurrencyItemBinding): CurrencyTypeVh(binding) {

        fun bind(currency: CurrencyView, listener: (currency: CurrencyView) -> Unit) =
            binding.apply {
                rootLL.setOnClickListener {
                    listener(currency)
                }
                rateTV.text = currency.rate.toString()
                val symbol = when (currency.secId) {
                    "CAD/RUB" -> " $"
                    "CHF/RUB" -> ""
                    "GBP/RUB" -> " £"
                    "JPY/RUB" -> " ¥"
                    "TRY/RUB" -> ""
                    "USD/RUB" -> " $"
                    else -> ""
                }
                val text = currency.secId + symbol
                secIdTV.text = text
            }
    }

    class DateVH(private val binding: VhHomeDateItemBinding): CurrencyTypeVh(binding) {

        fun bind(currency: CurrencyItems.DateViewItem) = binding.apply {
            dateTV.text = currency.date
        }
    }
}
