package com.example.foulette.ui.map

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.CONTEXT_RESTRICTED
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import com.example.foulette.databinding.FragmentKakaoMapBinding
import com.example.foulette.ui.base.BaseFragment
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class KakaoMapFragment :
    BaseFragment<FragmentKakaoMapBinding>(com.example.foulette.R.layout.fragment_kakao_map){
    private lateinit var mapView : MapView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initmap()
    }

    private fun initmap() {
        mapView = MapView(requireContext())
        val mapViewContainer = binding.mapView
        mapViewContainer.addView(mapView)
        mapView.setShowCurrentLocationMarker(true)
        startTracking()
    }

    // 현재 사용자 위치추적
    @SuppressLint("MissingPermission")
    private fun startTracking() {
        mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading  //이 부분
        val lm: LocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val userNowLocation: Location? = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        //위도 , 경도
        val uLatitude = userNowLocation?.latitude
        val uLongitude = userNowLocation?.longitude
        val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude!!, uLongitude!!)
    }

    // 위치추적 중지
    private fun stopTracking() {
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOff
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTracking()
    }



}