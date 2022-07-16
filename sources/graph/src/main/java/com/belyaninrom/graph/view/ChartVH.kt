package com.belyaninrom.graph.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.belyaninrom.graph.model.GraphAdapterItem
import com.belyaninrom.uicore.model.CurrencyView
import com.belyaninroom.graph.databinding.VhChartBinding

class ChartVH(val binding: VhChartBinding) : GraphVH(binding) {

    fun bind(items: List<GraphAdapterItem.Currency>) {
        setIsRecyclable(false)
        binding.graphCustomView.setItems(items.map {
            CurrencyView(
                it.tradeDate,
                it.tradeTime,
                it.secId,
                it.rate
            )
        }.toList())
    }
}