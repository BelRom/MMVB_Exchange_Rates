package com.belyaninrom.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.belyaninrom.home.view.CurrencyRVAdapter
import com.belyaninrom.home.model.Result
import com.belyaninrom.core_api.mediator.AppWithFacade
import com.belyaninrom.home.di.HomeComponent
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class HomeFragment: Fragment(R.layout.fragment_home) {

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    private var recyclerView: RecyclerView? = null
    private var adapter = CurrencyRVAdapter {
        findNavController().navigate(com.belyaninrom.main.R.id.action_homeFragment_to_graphFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        HomeComponent.create((requireActivity().application as AppWithFacade).getFacade())
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_home, null) as HomeView

        recyclerView = view.findViewById(R.id.recycler_view)
        val manager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = manager

        lifecycleScope.launch {
            viewModel.currenciesState.collect {
                when (it) {
                    is Result.StartLoading -> {
                        view.showProgressBar()
                    }
                    is Result.Success -> {
                        view.hideProgressBar()
                        adapter.setCurrencies(it.value)
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
        viewModel.getListCurrency()
        return view
    }
}