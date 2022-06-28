package com.belyaninrom.graph.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.belyaninrom.uicore.model.CurrencyView
import com.belyaninroom.graph.databinding.VhCurrencyItemBinding

class CurrencyVH(val binding: VhCurrencyItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind (currency: CurrencyView) {
        binding.rateTV.text = currency.rate.toString()
        binding.dateTV.text = currency.tradeDate
    }
}