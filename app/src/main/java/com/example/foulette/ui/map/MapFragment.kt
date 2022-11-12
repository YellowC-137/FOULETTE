package com.example.foulette.ui.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.foulette.BuildConfig
import com.example.foulette.FouletteApplication
import com.example.foulette.R
import com.example.foulette.databinding.FragmentMapBinding
import com.example.foulette.domain.models.HistoryResult
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.ui.base.BaseFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map),
    OnMapReadyCallback {
    private val viewModel: MapViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap
    private lateinit var placesClient: PlacesClient
    private lateinit var selectedRestaurant: RestaurantResult

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val args: MapFragmentArgs by navArgs()
        /*
        <argument
            android:name="selectedRestaurant"
            app:argType="com.example.foulette.domain.models.RestaurantResult"/>
        * */
        //selectedRestaurant = args.selectedRestaurant


        Places.initialize(FouletteApplication.ApplicationContext(), BuildConfig.MAPS_API_KEY)
        placesClient = Places.createClient(requireContext())
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        save()
        //collectFlow()
    }

    private fun save() {
        val saveRestaurant = HistoryResult(
            0,
            restaurantName = "TEST",
            restaurantLocLog = 37.53462435,
            restaurantLocLat = 123.3253,
            restaurantAddress = "test",
            restaurantImgUrl = "", date = ""
        )
        viewModel.saveHistory(saveRestaurant)
        Timber.e("SAVE!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
    }

    private fun collectFlow() {
        //TODO("Not yet implemented")
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        this.map = googleMap
        map.isMyLocationEnabled = true
        map.uiSettings?.isMyLocationButtonEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener {
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        it.latitude,
                        it.longitude
                    ), 15F
                )
            )
            val start = "${it.latitude},${it.longitude}"



        }


    }

}
