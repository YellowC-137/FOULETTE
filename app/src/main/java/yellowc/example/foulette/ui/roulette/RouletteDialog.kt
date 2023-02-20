package yellowc.example.foulette.ui.roulette

import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import com.bluehomestudio.luckywheel.LuckyWheel
import com.bluehomestudio.luckywheel.WheelItem
import com.google.android.material.snackbar.Snackbar
import yellowc.example.foulette.R
import yellowc.example.foulette.databinding.DialogRouletteBinding
import yellowc.example.foulette.ui.base.BaseDialog
import yellowc.example.foulette.ui.main.MainViewModel
import java.security.SecureRandom

class RouletteDialog() : BaseDialog<DialogRouletteBinding>(R.layout.dialog_roulette) {
    private lateinit var luckyWheel: LuckyWheel
    private val viewModel: MainViewModel by activityViewModels()
    private val wheelItems: MutableList<WheelItem> = ArrayList()

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (parentFragment is DialogInterface.OnDismissListener) {
            (parentFragment as DialogInterface.OnDismissListener).onDismiss(dialog)
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
        val img = listOf(
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five,
            R.drawable.six
        )
        for (i in img) {
            wheelItems.add(
                WheelItem(
                    resources.getColor(R.color.gray_orange),
                    BitmapFactory.decodeResource(resources, i), ""
                )
            )
        }
    }


    private fun initView() {
        dialog?.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCanceledOnTouchOutside(false)
        }
        binding.apply {
            val random = SecureRandom().nextInt(5) + 1
            roulette.addWheelItems(wheelItems)
            roulette.setLuckyWheelReachTheTarget {
                Snackbar.make(
                    requireView(),
                    "찾았다!",
                    Snackbar.LENGTH_LONG
                ).show()
                viewModel.setRouletteState(RouletteState.finish)
                dismiss()
            }

            roulette.rotateWheelTo(random)
        }
    }


}

