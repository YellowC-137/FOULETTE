package com.example.foulette.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.foulette.R
import com.example.foulette.databinding.FragmentMainBinding
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.ui.base.BaseFragment
import com.example.foulette.util.REQUEST_CODE
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        initView()
        //collectFlow()

    }

    private fun initView() {
        binding.apply {
            btnSearchFromMylocation.setOnClickListener {
                val foulettePick: RestaurantResult = RestaurantResult(
                    id = 0,
                    name = "",
                    type = "",
                    latitude = 0.0,
                    longitude = 0.0,
                    rate = 0.0,
                    ImgUrl = ""
                )
                requiresPermission() //TODO 권한 수정
                // 현재 위치 받아오기 -> 음식점 검색 -> 추리기 및 애니매이션 -> 넘기기
                //startSearch()
                val toMap = MainFragmentDirections.actionMainFragmentToMapFragment()
                requireView().findNavController().navigate(toMap)
            }
            fabHistory.setOnClickListener {
                val toHistory = MainFragmentDirections.actionMainFragmentToHistoryFragment()
                requireView().findNavController().navigate(toHistory)
            }
            fabSetting.setOnClickListener {
                //startSettingDialog()
                requiresPermission()
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
            getMyLocation()
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
        fusedLocationClient.lastLocation.addOnSuccessListener {
            val latlng : String = it.latitude.toString()+","+it.longitude.toString()
            viewModel.getRestaurant(latlng)
        }
    }

    private fun startSettingDialog() {
        TODO("Not yet implemented")
    }

    private fun startSearch() {
        TODO("Not yet implemented")
    }

    private fun setLocation() {
        TODO("Not yet implemented")
    }


}