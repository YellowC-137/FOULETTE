package com.example.foulette.ui.map

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.foulette.databinding.FragmentKakaoMapBinding
import com.example.foulette.domain.models.KakaoResult
import com.example.foulette.ui.base.BaseFragment
import com.example.foulette.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import timber.log.Timber


@AndroidEntryPoint
class KakaoMapFragment :
    BaseFragment<FragmentKakaoMapBinding>(com.example.foulette.R.layout.fragment_kakao_map) {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var mapView: MapView
    private lateinit var testlist : List<KakaoResult>
    private val testLoc : Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initmap()
    }

    private fun initmap() {
        mapView = MapView(requireActivity())
        val mapViewContainer = binding.mapView
        mapViewContainer.addView(mapView)
        mapView.setShowCurrentLocationMarker(true)
        //startTracking()
    }

    private fun startSearch(myLatitude: String, myLongitude: String) {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getKakoSearch(myLatitude, myLongitude)

                viewModel.kakaoList.collect {
                    Timber.e(it.size.toString())
                    for (result in it) {
                        Timber.e("x,y = ${result.x} , ${result.y}")
                        val name = result.place_name
                        val X = result.x.toDouble()
                        val Y = result.y.toDouble()
                        //getMarker(X,Y, name,it.indexOf(result))

                        val marker = MapPOIItem()
                        marker.isMoveToCenterOnSelect=true
                        marker.itemName = name
                        marker.tag = it.indexOf(result)
                        marker.mapPoint = MapPoint.mapPointWithGeoCoord(X,Y)
                        marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
                        marker.selectedMarkerType =
                            MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                        mapView.addPOIItem(marker)
                    }
                }
            }
        }
        //getMarkers(testlist)



    }

    private fun getMarkers(mylist: List<KakaoResult>) {
        for (i in mylist) {
            getMarker(i.x.toDouble(), i.y.toDouble(),i.place_name,mylist.indexOf(i))
            Timber.e(i.x+" "+i.y)
        }
    }

    private fun getMarker(X: Double, Y: Double, name: String,i:Int) {
        val marker = MapPOIItem()
        marker.isMoveToCenterOnSelect=true
        marker.itemName = name
        marker.tag = i
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(X,Y)
        marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
        marker.selectedMarkerType =
            MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker)
        Timber.e("x,y : ${X} , ${Y}")
    }

    // 현재 사용자 위치추적
    @SuppressLint("MissingPermission")
    private fun startTracking() {
        mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading  //이 부분
        val lm: LocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val userNowLocation: Location? = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        //위도 , 경도
        val uLatitude = userNowLocation?.latitude
        val uLongitude = userNowLocation?.longitude
        val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude!!, uLongitude!!)
        startSearch(uLatitude.toString(), uLongitude.toString())
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