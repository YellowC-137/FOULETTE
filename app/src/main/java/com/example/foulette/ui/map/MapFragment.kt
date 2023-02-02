package com.example.foulette.ui.map

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foulette.BuildConfig
import com.example.foulette.FouletteApplication
import com.example.foulette.R
import com.example.foulette.databinding.FragmentMapBindingImpl
import com.example.foulette.domain.models.HistoryResult
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.domain.models.TmapRouteResult
import com.example.foulette.ui.base.BaseFragment
import com.example.foulette.ui.main.MainFragmentDirections
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.RoundCap
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MapFragment :
    BaseFragment<com.example.foulette.databinding.FragmentMapBinding>(R.layout.fragment_map),
    OnMapReadyCallback {
    private val viewModel: MapViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap
    private lateinit var placesClient: PlacesClient
    private lateinit var selectedRestaurant: RestaurantResult
    private lateinit var routeData: TmapRouteResult
    private lateinit var geocoder: Geocoder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: MapFragmentArgs by navArgs()
        selectedRestaurant = args.restaurant
        routeData = args.routes
        Places.initialize(FouletteApplication.ApplicationContext(), BuildConfig.MAPS_API_KEY)
        placesClient = Places.createClient(requireContext())
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        binding.btnHome.setOnClickListener {
            val toMain = MapFragmentDirections.actionMapFragmentToMainFragment()
            requireView().findNavController().navigate(toMain)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        geocoder = Geocoder(requireContext())
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        this.map = googleMap
        map.isMyLocationEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
        val address = geocoder.getFromLocation(
            selectedRestaurant.latitude!!,
            selectedRestaurant.longitude!!,
            1
        )
        val now = SimpleDateFormat("yyyy-MM-dd hh:mm").format(Date(System.currentTimeMillis()))
        fusedLocationClient.lastLocation.addOnSuccessListener {
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        it.latitude,
                        it.longitude
                    ), 15F
                )
            )
            viewModel.saveHistory(
                historyResult = HistoryResult(
                    id = 0,
                    restaurantName = selectedRestaurant.name!!,
                    restaurantImgUrl = selectedRestaurant.ImgUrl!!,
                    restaurantAddress = address[0].subLocality,
                    date = now,
                    restaurantLocLat = selectedRestaurant.latitude!!,
                    restaurantLocLog = selectedRestaurant.longitude!!
                )
            )
        }
        //TODO 경로 결과값에서 거리/1000 , 시간/60 으로 나누어 주어야함.

        val polylineOptions = PolylineOptions()
        val final = LatLng(selectedRestaurant.latitude!!, selectedRestaurant.longitude!!)
        for (i in routeData.coordinates) {
            polylineOptions.add(LatLng(i[1].toDouble(), i[0].toDouble()))
        }
        val polyline = map.addPolyline(polylineOptions)
        polyline.color = Color.MAGENTA
        polyline.endCap = RoundCap()
        polyline.startCap = RoundCap()
        map.addMarker(
            MarkerOptions().position(final).title(selectedRestaurant.name).visible(true)
        )
        map.moveCamera(CameraUpdateFactory.newLatLng(final))
    }

}
