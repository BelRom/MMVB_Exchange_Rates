package com.belyaninrom.graph.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.belyaninrom.graph.model.GraphAdapterItem
import com.belyaninrom.uicore.model.CurrencyView
import com.belyaninroom.graph.databinding.VhChartBinding
import com.belyaninroom.graph.databinding.VhCurrencyItemBinding
import com.belyaninroom.graph.databinding.VhSelectDateBinding
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class GraphRVAdapter(val fragmentManager: FragmentManager, val listener: Listener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_CURRENCY = 0
        const val TYPE_CHART = 1
        const val TYPE_DATE = 2
    }

    val items = ArrayList<GraphAdapterItem>()

    interface Listener {
        fun selectedNewDate(selectedDate: GraphAdapterItem.SelectedDate)
    }

    override fun getItemViewType(position: Int): Int = when (items.get(position)) {
        is GraphAdapterItem.Currency -> TYPE_CURRENCY
        is GraphAdapterItem.Chart -> TYPE_CHART
        is GraphAdapterItem.SelectedDate -> TYPE_DATE
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_CHART -> {
                ChartVH(
                    VhChartBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            TYPE_CURRENCY -> {
                CurrencyVH(
                    VhCurrencyItemBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            TYPE_DATE -> {
                SelectDateVH(
                    VhSelectDateBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            else -> error("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items.get(position)) {
            is GraphAdapterItem.Currency -> (holder as CurrencyVH).bind(item)
            is GraphAdapterItem.Chart -> (holder as ChartVH).bind(items.filter {
                it is GraphAdapterItem.Currency
            }.toList() as List<GraphAdapterItem.Currency>)
            is GraphAdapterItem.SelectedDate -> (holder as SelectDateVH).bind(item, fragmentManager){
                listener.selectedNewDate(it)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun setCurrencies(value: List<CurrencyView>) {
        if (value.isEmpty())
            return
        items.clear()
        val format = SimpleDateFormat("yyyy-MM-dd")
        items.add(GraphAdapterItem.SelectedDate(format.parse(value.first().tradeDate), format.parse(value.last().tradeDate)))
        items.add(GraphAdapterItem.Chart())
        items.addAll(value.map {
            GraphAdapterItem.Currency(
                it.tradeDate,
                it.tradeTime,
                it.secId,
                it.rate
            )
        }.toList())
        notifyDataSetChanged()
    }
}
