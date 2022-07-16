package com.belyaninrom.graph.view

import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.belyaninrom.graph.model.GraphAdapterItem
import com.belyaninroom.graph.R
import com.belyaninroom.graph.databinding.VhSelectDateBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class SelectDateVH(val binding: VhSelectDateBinding) : GraphVH(binding) {

    val format = SimpleDateFormat("dd.MM.yyyy")

    fun bind(
        selectedDate: GraphAdapterItem.SelectedDate,
        fragmentManager: FragmentManager,
        callback: (selectedDate: GraphAdapterItem.SelectedDate) -> Unit
    ) {
        binding.firatDateTV.text = format.format(selectedDate.firstDate)
        binding.lastDateTV.text = format.format(selectedDate.lastDate)
        binding.firatDateTV.setOnClickListener {
            pickDay(binding.firatDateTV, fragmentManager) {
                selectedDate.firstDate = it
                callback(selectedDate)
            }
        }
        binding.lastDateTV.setOnClickListener {
            pickDay(binding.lastDateTV, fragmentManager) {
                selectedDate.firstDate = it
                callback(selectedDate)
            }
        }
    }

    private fun pickDay(
        textView: TextView,
        fragmentManager: FragmentManager,
        callback: (dateNew: Date) -> Unit
    ) {
        val format = SimpleDateFormat("dd.MM.yyyy")

        val datePickerBuilder =
            MaterialDatePicker.Builder.datePicker()
                .setTheme(R.style.ThemeOverlay_App_DatePicker)

        val date = textView.text.toString()
        try {
            val date = format.parse(date)
            var time = date.time
            val timezone = TimeZone.getDefault()
            time += timezone.getOffset(time)

            datePickerBuilder.setSelection(time)
        } catch (e: Exception) {
            Toast.makeText(textView.context, "Произошла ошибка ${e.message}", Toast.LENGTH_LONG)
                .show()
        }
        val datePicker = datePickerBuilder.build()


        datePicker.addOnPositiveButtonClickListener {
            val date = format.format(it)
            textView.setText(date)
            callback.invoke(Date(it))
        }
        datePicker.show(fragmentManager, "pickBirthday")
    }
}