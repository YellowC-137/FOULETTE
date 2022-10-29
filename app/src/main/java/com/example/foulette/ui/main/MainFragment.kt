package com.example.foulette.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.example.foulette.R
import com.example.foulette.databinding.FragmentMainBinding
import com.example.foulette.ui.base.BaseFragment

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSearch.setOnClickListener {
            val toMap = MainFragmentDirections.actionMainFragmentToMapFragment()
            requireView().findNavController().navigate(toMap)
        }

    }


}