package com.example.foulette.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foulette.R
import com.example.foulette.databinding.DialogSettingBinding
import com.example.foulette.ui.base.BaseDialog

class SettingDialog():BaseDialog<DialogSettingBinding>(R.layout.dialog_setting) {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = super.onCreateView(inflater, container, savedInstanceState)
        initView()
        setSharedPreference()
        return binding
    }

    private fun setSharedPreference() {
        //TODO("Not yet implemented")
    }

    private fun initView() {
        binding.apply {

        }
    }

}