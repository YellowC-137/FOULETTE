package com.example.foulette.ui.history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foulette.R
import com.example.foulette.databinding.FragmentHistoryBinding
import com.example.foulette.ui.base.BaseFragment

class HistoryFragment : BaseFragment<FragmentHistoryBinding>(R.layout.fragment_history) {
    private lateinit var viewModel: HistoryViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}