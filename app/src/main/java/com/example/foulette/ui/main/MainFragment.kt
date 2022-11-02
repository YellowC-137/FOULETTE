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
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.ui.base.BaseFragment
import com.example.foulette.util.REQUEST_CODE
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        initView()
        requiresPermission()
    }

    private fun initView() {
        binding.apply {
            btnSearchFromMylocation.setOnClickListener {
                // 현재 위치 받아오기 -> 음식점 검색 -> 추리기 및 애니매이션 -> 넘기기
                getMyLocation()
            }
            fabHistory.setOnClickListener {
                val toHistory = MainFragmentDirections.actionMainFragmentToHistoryFragment()
                requireView().findNavController().navigate(toHistory)
            }
            fabSetting.setOnClickListener {
                //startSettingDialog()
                playRoulette()
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
                perms = *perms
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun getMyLocation() {

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    fusedLocationClient.lastLocation.addOnSuccessListener {
                        val latlng: String = it.latitude.toString() + "," + it.longitude.toString()
                        viewModel.getRestaurant(latlng)
                    }
                }
            }


    }

    private fun playRoulette() {
        RouletteDialog().show(
            requireActivity().supportFragmentManager,
            "RouletteDialog"
        )
        /*val toMap = MainFragmentDirections.actionMainFragmentToMapFragment()
                requireView().findNavController().navigate(toMap)
        * */
    }

}