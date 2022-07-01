package com.belyaninrom.graph

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.belyaninrom.core_api.mediator.AppWithFacade
import com.belyaninrom.core_api.model.Result
import com.belyaninrom.graph.di.GraphComponent
import com.belyaninrom.graph.model.GraphAdapterItem
import com.belyaninrom.graph.view.GraphRVAdapter
import com.belyaninrom.graph.view.GraphView
import com.belyaninroom.graph.R
import com.belyaninroom.graph.databinding.FragmentGraphBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GraphFragment: Fragment(R.layout.fragment_graph) {

    val binding by viewBinding(FragmentGraphBinding::bind)

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    private var adapter: GraphRVAdapter? = null
    private val format = SimpleDateFormat("yyyy-MM-dd")

    val args: GraphFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        GraphComponent.create((requireActivity().application as AppWithFacade).getFacade())
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val view = view as GraphView
        val manager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = GraphRVAdapter(parentFragmentManager, object: GraphRVAdapter.Listener {
            override fun selectedNewDate(selectedDate: GraphAdapterItem.SelectedDate) {
                viewModel.getListCurrency(args.secId, format.format(selectedDate.firstDate), format.format(selectedDate.lastDate))
            }
        })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = manager

        lifecycleScope.launch {
            viewModel.graphState.collect {
                when (it) {
                    is Result.StartLoading -> {
                        view.showProgressBar()
                    }
                    is Result.Success -> {
                        view.hideProgressBar()
                        adapter?.setCurrencies(it.value)
                    }
                    is Result.Error -> {
                        view.hideProgressBar()
                        Toast.makeText(requireContext(),
                            "произола ошибка ${it.throwable?.message}", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val date = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)
        viewModel.getListCurrency(
            args.secId,
            "2022-05-06",
            date
        )
    }
}