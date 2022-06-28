package com.belyaninrom.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.belyaninrom.home.R
import com.belyaninrom.uicore.model.CurrencyView

class CurrencyRVAdapter(val listener: (currencyNetwork: CurrencyView) -> Unit): RecyclerView.Adapter<CurrencyVH>() {

    val items = ArrayList<CurrencyView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vh_home_currency_item, parent, false)
        return CurrencyVH(view)
    }

    override fun onBindViewHolder(holder: CurrencyVH, position: Int) {
        holder.bind(items.get(position), listener)
    }

    override fun getItemCount(): Int = items.size

    fun setCurrencies(value: List<CurrencyView>) {
        items.clear()
        items.addAll(value)
        notifyDataSetChanged()
    }
}