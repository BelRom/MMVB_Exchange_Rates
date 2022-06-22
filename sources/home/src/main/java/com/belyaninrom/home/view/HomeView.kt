package com.belyaninrom.home

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.belyaninrom.home.databinding.FragmentHomeBinding

class HomeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), IHomeView {

    val binding by viewBinding (FragmentHomeBinding::bind)


//    override fun setCurrencies(fact: List<Currency>) {
//
//    }

    override fun showProgressBar() {
        binding.progressBar.isVisible = true
    }

    override fun hideProgressBar() {
        binding.progressBar.isVisible = false
    }
}

interface IHomeView {

//    fun setCurrencies(fact: List<Currency>)

    fun showProgressBar()

    fun hideProgressBar()
}