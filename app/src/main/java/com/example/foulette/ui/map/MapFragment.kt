package com.example.foulette.ui.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.foulette.BuildConfig
import com.example.foulette.FouletteApplication
import com.example.foulette.R
import com.example.foulette.databinding.FragmentMapBinding
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.ui.base.BaseFragment
import com.example.foulette.ui.main.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.apache.commons.lang3.ObjectUtils.Null
import timber.log.Timber

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map),
    OnMapReadyCallback {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val seoul = LatLng(37.5666805, 126.9784147)
    private lateinit var map: GoogleMap
    private lateinit var placesClient: PlacesClient
    private lateinit var selectedRestaurant: RestaurantResult

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val args: MapFragmentArgs by navArgs()
        //selectedRestaurant = args.restaurant

        Places.initialize(FouletteApplication.ApplicationContext(), BuildConfig.MAPS_API_KEY)
        placesClient = Places.createClient(requireContext())
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        this.map = googleMap
        map.isMyLocationEnabled = true
        map.uiSettings?.isMyLocationButtonEnabled = true
        map.addMarker(MarkerOptions().position(seoul).title("SEOUL"))
        fusedLocationClient.lastLocation.addOnSuccessListener {
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        it.latitude,
                        it.longitude
                    ), map.maxZoomLevel
                )
            )
            val start = "${it.latitude},${it.longitude}"
            viewModel.getRoute(it.longitude, it.latitude, 127.054154, 37.448604, "출발지", "도착지")
            //x,y가 반대임 , x:127~ y:37~

//response가 리스트가 아닌 데이터 그자체? , 타입이 점일때와 선일때가 달라서 그렇다!


            /*kotlinx.serialization.json.internal.JsonDecodingException: Unexpected JSON token at offset 132: Expected start of the array '[', but had '[' instead
                 JSON input: ....."Point",       "coordinates": [127.05670593180979,37.4554606
            * */
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.routeList.collect {
                        val polylineOptions = PolylineOptions()
                        for (roads in it) {
                            if (it is Null) {
                                Timber.e("XXXXXXX")
                            } else {
                                Timber.e(roads.pointX + "," + roads.pointY)
                            }
                            //polylineOptions.add(LatLng(roads.pointX.toDouble(), roads.pointY.toDouble()))

                        }
                    }
                }
            }


        }


    }

}
