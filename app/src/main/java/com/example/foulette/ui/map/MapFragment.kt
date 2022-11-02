package com.example.foulette.ui.map

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.foulette.BuildConfig
import com.example.foulette.FouletteApplication
import com.example.foulette.R
import com.example.foulette.databinding.FragmentMapBinding
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.ui.base.BaseFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import timber.log.Timber

class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map),
    OnMapReadyCallback {
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
        fusedLocationClient.lastLocation.addOnSuccessListener{
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        it.latitude,
                        it.longitude
                    ), map.maxZoomLevel
                )
            )
        }
    }

}
