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
import com.example.foulette.ui.roulette.RouletteDialog
import com.example.foulette.util.REQUEST_CODE
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.security.SecureRandom
import java.util.stream.Collectors.toMap

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        initView()
        requiresPermission()
        //collectFlow()
    }

    private fun collectFlow() {
        //TODO("Not yet implemented")
        //val toMap = MainFragmentDirections.actionMainFragmentToMapFragment()
        //requireView().findNavController().navigate(toMap)
    }

    //TODO api call 수정, 권한 수정 ,Flow

    private fun initView() {
        binding.apply {
            btnSearchFromMylocation.setOnClickListener {
                getMyLocation()
                //playRoulette()  , 룰렛 상태에 따라서 호출
                //val toMap = MainFragmentDirections.actionMainFragmentToMapFragment()
                //requireView().findNavController().navigate(toMap)
            }
            fabHistory.setOnClickListener {
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
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.restaurantList.collect {
                        val random = SecureRandom().nextInt(it.size)
                        val selected = it[random]
                        toMap(selected)
                    }
                }
            }
        }
    }

    private fun toMap(selected: RestaurantResult) {
        val toMap = MainFragmentDirections.actionMainFragmentToMapFragment(selected!!)
        requireView().findNavController().navigate(toMap)
    }


    private fun playRoulette() {
        RouletteDialog().show(
            requireActivity().supportFragmentManager,
            "RouletteDialog"
        )
    }

}