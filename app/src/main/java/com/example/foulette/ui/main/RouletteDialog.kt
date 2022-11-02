package com.example.foulette.ui.main

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bluehomestudio.luckywheel.LuckyWheel
import com.bluehomestudio.luckywheel.WheelItem
import com.example.foulette.R
import com.example.foulette.databinding.DialogRouletteBinding
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.ui.base.BaseDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.random.Random

class RouletteDialog() : BaseDialog<DialogRouletteBinding>(R.layout.dialog_roulette) {
    private lateinit var luckyWheel: LuckyWheel
    private val viewModel: MainViewModel by activityViewModels()
    private val wheelItems: MutableList<WheelItem> = ArrayList()
    private val launcher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {

            }

        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding = super.onCreateView(inflater, container, savedInstanceState)
        //collectFlow()
        setRoulette()
        initView()
        return binding
    }

    private fun setRoulette() {
        for (i in 0..9){
            wheelItems.add(WheelItem(Color.RED,
                BitmapFactory.decodeResource(resources, R.drawable.img),
                "text ${i+1}"))
        }

    }


    private fun initView() {
        dialog?.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCanceledOnTouchOutside(true)
        }
        binding.apply {
            //TODO wheelItems,Random 수정
            val random = Random(wheelItems.size).nextInt(10)
            roulette.addWheelItems(wheelItems)
            btnRoulette.setOnClickListener {
                roulette.rotateWheelTo(random)
            }
            roulette.setLuckyWheelReachTheTarget {
                val rouletteResult = wheelItems[random].text
                Snackbar.make(
                    requireView(),
                    rouletteResult,
                    Snackbar.LENGTH_LONG
                ).show()
            }


        }
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.restaurantList.collect {
                    Timber.e(it.size.toString())
                    for (result in it) {
                        wheelItems.add(
                            WheelItem(
                                Color.RED,
                                BitmapFactory.decodeResource(
                                    resources,
                                    R.drawable.ic_launcher_foreground
                                ),
                                result.name
                            )
                        )
                    }
                }
            }
        }
    }


}