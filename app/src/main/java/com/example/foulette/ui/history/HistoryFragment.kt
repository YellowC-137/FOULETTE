package com.example.foulette.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.foulette.R
import com.example.foulette.databinding.FragmentHistoryBinding
import com.example.foulette.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(R.layout.fragment_history) {
    private val viewModel: HistoryViewModel by viewModels()
    private val pagingAdapter: HistoryAdapter by lazy {
        HistoryAdapter(
            deleteClicked = {
                viewModel.deleteHistoryByName(it.restaurantName)
                Snackbar.make(binding.root,"삭제되었습니다.",Snackbar.LENGTH_SHORT).show()
            },
            itemClicked = {
                //TODO Map 상태 관리
                //val toMap = HistoryFragmentDirections.actionHistoryFragmentToMapFragment(it)
                //requireView().findNavController().navigate(toMap)
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        collectFlow()
        viewModel.getAllHistory()
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.historyData.collectLatest { it ->
                    pagingAdapter.submitData(it)
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            rcvHistory.adapter = pagingAdapter
        }
    }

}