package com.belyaninrom.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.belyaninrom.home.databinding.VhHomeCurrencyItemBinding
import com.belyaninrom.home.databinding.VhHomeDateItemBinding
import com.belyaninrom.uicore.model.CurrencyView
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat

class CurrencyRVAdapter(val listener: (currencyNetwork: CurrencyView) -> Unit) :
    RecyclerView.Adapter<CurrencyTypeVh>() {

    companion object {
        const val TYPE_CURRENCY = 0
        const val TYPE_DATE = 1
    }

    val items = ArrayList<CurrencyItems>()
    val format = SimpleDateFormat("yyyy-MM-dd")
    val formatDate = SimpleDateFormat("dd.MM.yyyy")

    override fun getItemViewType(position: Int): Int {
        val item = items.get(position)
        return when (item) {
            is CurrencyItems.CurrencyViewItem -> {
                TYPE_CURRENCY
            }
            is CurrencyItems.DateViewItem -> {
                TYPE_DATE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyTypeVh {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_CURRENCY ->
                CurrencyTypeVh.CurrencyVH(
                    VhHomeCurrencyItemBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            TYPE_DATE ->
                CurrencyTypeVh.DateVH(
                    VhHomeDateItemBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            else -> throw IllegalArgumentException("no this type")
        }
    }

    override fun onBindViewHolder(holder: CurrencyTypeVh, position: Int) {
        when (holder) {
            is CurrencyTypeVh.CurrencyVH -> holder.bind(items.get(position) as CurrencyItems.CurrencyViewItem, listener)
            is CurrencyTypeVh.DateVH -> holder.bind(items.get(position) as CurrencyItems.DateViewItem)
        }

    }

    override fun getItemCount(): Int = items.size

    fun setCurrencies(value: List<CurrencyView>) {
        items.clear()
        if (value.isNotEmpty()) {
            val date = format.parse(value.get(0).tradeDate)
            date?.let {
                items.add(CurrencyItems.DateViewItem(formatDate.format(it)))
            }
        }
        items.addAll(value.map {
            CurrencyItems.CurrencyViewItem(it.tradeDate, it.tradeTime, it.secId, it.rate)
        }.toList())
        notifyDataSetChanged()
    }
}

sealed interface CurrencyItems {
    class CurrencyViewItem(
        tradeDate: String,
        tradeTime: String,
        secId: String,
        rate: Double
    ) : CurrencyView(
        tradeDate,
        tradeTime,
        secId,
        rate
    ), CurrencyItems
    data class DateViewItem(val date: String): CurrencyItems
}