package com.example.foulette.ui.roulette

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
import androidx.navigation.findNavController
import com.bluehomestudio.luckywheel.LuckyWheel
import com.bluehomestudio.luckywheel.WheelItem
import com.example.foulette.R
import com.example.foulette.databinding.DialogRouletteBinding
import com.example.foulette.ui.base.BaseDialog
import com.example.foulette.ui.main.MainFragmentDirections
import com.example.foulette.ui.main.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import timber.log.Timber
import java.security.SecureRandom

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
        savedInstanceState: Bundle?
    ): View? {

        val binding = super.onCreateView(inflater, container, savedInstanceState)
        setRoulette()
        initView()
        return binding
    }

    private fun setRoulette() {
        val img = listOf<Int>(R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five,R.drawable.six)
        for (i in img) {
            wheelItems.add(
                WheelItem(
                    resources.getColor(R.color.gray_orange),
                    BitmapFactory.decodeResource(resources, i),
                    ""
                )
            )
        }

    }


    private fun initView() {
        val random = SecureRandom().nextInt(5)+1
        dialog?.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCanceledOnTouchOutside(true)
        }
        binding.apply {
            roulette.addWheelItems(wheelItems)
            roulette.setLuckyWheelReachTheTarget {
                Snackbar.make(
                    requireView(),
                    "찾았다!",
                    Snackbar.LENGTH_LONG
                ).show()
                showMap()
            }
            roulette.rotateWheelTo(random)
        }
    }

    private fun showMap() {
        dismiss()
    }

}