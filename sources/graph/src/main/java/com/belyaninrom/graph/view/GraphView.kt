package com.belyaninrom.graph.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.belyaninroom.graph.databinding.FragmentGraphBinding

class GraphView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), IGraphView {

    val binding by viewBinding (FragmentGraphBinding::bind)

    override fun showProgressBar() {
        binding.progressBar.isVisible = true
    }

    override fun hideProgressBar() {
        binding.progressBar.isVisible = false
    }
}

interface IGraphView {

    fun showProgressBar()

    fun hideProgressBar()
}