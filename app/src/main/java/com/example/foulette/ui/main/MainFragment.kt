package com.example.foulette.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.foulette.R
import com.example.foulette.databinding.FragmentMainBinding
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.domain.models.TmapRouteResult
import com.example.foulette.ui.base.BaseFragment
import com.example.foulette.ui.roulette.RouletteDialog
import com.example.foulette.ui.roulette.RouletteState
import com.example.foulette.util.REQUEST_CODE
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
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
    private lateinit var route: TmapRouteResult
    private lateinit var myLoc: Location


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        requiresPermission()
        initView()
        collectFlow()
        viewModel.setRouletteState(RouletteState.closed)
    }

    //TODO 권한 수정
    private fun initView() {
        binding.apply {
            btnSearchFromMylocation.setOnClickListener {
                setData()
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
            //TODO 실행
        } else {
            EasyPermissions.requestPermissions(
                host = this,
                rationale = "PERMISSIONS",
                requestCode = REQUEST_CODE,
                perms = perms
            )
            Snackbar.make(requireView(), "권한이 필요합니다.", Snackbar.LENGTH_LONG).show()
            //TODO 앱 종료
        }
    }

    @SuppressLint("MissingPermission")
    private fun setData() {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            myLoc = it
            val latlng: String = it.latitude.toString() + "," + it.longitude.toString()
            viewModel.getRestaurant(latlng)
        }
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.restaurantList.collectLatest {
                    if (it.isNotEmpty()) {
                        random = SecureRandom().nextInt(it.size)
                        result = it[random]
                        viewModel.getRoute(
                            myLoc.longitude,
                            myLoc.latitude,
                            result.longitude!!.toDouble(),
                            result.latitude!!.toDouble(),
                            "내 위치",
                            result.name!!
                        )
                        Timber.e("restaurant : ${result.name}")
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.routesData.collectLatest {
                    if (it.isNotEmpty()){
                        for (routes in it) {
                            route = routes
                            Timber.e("routeList : ${routes.totalTime}")
                            //playRoulette이 여러번 호출되어서 내비게이션 오류가 발생한다.
                        }
                        playRoulette()
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
        //TODO 내비게이션 에러!
        val toMap = MainFragmentDirections.actionMainFragmentToMapFragment(result, route)
        requireView().findNavController().navigate(toMap)

    }

}



