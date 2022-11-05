package com.example.foulette.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.foulette.R
import com.example.foulette.databinding.FragmentHistoryBinding
import com.example.foulette.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(R.layout.fragment_history) {
    private val viewModel: HistoryViewModel by viewModels()
    private val pagingAdapter: HistoryAdapter by lazy {
        HistoryAdapter(
            deleteClicked = {
                viewModel.deleteHistoryById(it.id)
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

        viewModel.getAllHistory()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        binding.apply {
            rcvHistory.adapter = pagingAdapter
        }
    }

}