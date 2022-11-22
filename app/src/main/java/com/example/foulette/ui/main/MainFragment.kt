package com.example.foulette.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.foulette.R
import com.example.foulette.databinding.FragmentMainBinding
import com.example.foulette.domain.models.HistoryResult
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.ui.base.BaseFragment
import com.example.foulette.ui.roulette.RouletteDialog
import com.example.foulette.ui.roulette.RouletteState
import com.example.foulette.util.REQUEST_CODE
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.security.SecureRandom
import kotlin.properties.Delegates

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var random by Delegates.notNull<Int>()
    private lateinit var result: RestaurantResult

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        initView()
        requiresPermission()
        getMyLocation() //TODO 호출시기
        collectFlow()
    }

    //TODO 권한 수정, 룰렛 stateFlow

    private fun initView() {
        binding.apply {
            btnSearchFromMylocation.setOnClickListener {
                playRoulette()
            }
            fabFavorite.setOnClickListener {
                val toHistory = MainFragmentDirections.actionMainFragmentToHistoryFragment()
                requireView().findNavController().navigate(toHistory)
            }
        }
    }

    @AfterPermissionGranted(REQUEST_CODE)
    private fun requiresPermission() {
        val perms = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE
        )
        if (EasyPermissions.hasPermissions(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE
            )
        ) {
        } else {
            EasyPermissions.requestPermissions(
                host = this,
                rationale = "PERMISSIONS",
                requestCode = REQUEST_CODE,
                perms = perms
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun getMyLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            val latlng: String = it.latitude.toString() + "," + it.longitude.toString()
            viewModel.getRestaurant(latlng)
        }
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.restaurantList.collectLatest {
                    if (it.isNotEmpty()) {
                        random = SecureRandom().nextInt(it.size) + 1
                        result = it[random]
                        Timber.e("${random} : ${result.name}")
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.rouletteState.collect { rouletteState ->
                    if (rouletteState == RouletteState.finish) {
                        viewModel.saveResult(
                            HistoryResult(
                                id = 0,
                                date = "",
                                restaurantName = result.name!!,
                                restaurantAddress = "",
                                restaurantImgUrl = result.ImgUrl!!,
                                restaurantLocLog = result.longitude!!,
                                restaurantLocLat = result.latitude!!,
                            )
                        )
                        viewModel.setRouletteState(RouletteState.closed)
                        val toMap = MainFragmentDirections.actionMainFragmentToMapFragment(result)
                        requireView().findNavController().navigate(toMap)
                    }

                }
            }
        }

    }


    private fun playRoulette() {
        viewModel.setRouletteState(RouletteState.playing)
        RouletteDialog().show(
            requireActivity().supportFragmentManager,
            "Roulette"
        )
    }


}