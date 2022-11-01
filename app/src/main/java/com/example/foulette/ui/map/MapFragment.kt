package com.example.foulette.ui.map

import android.Manifest
import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.example.foulette.BuildConfig
import com.example.foulette.FouletteApplication
import com.example.foulette.R
import com.example.foulette.databinding.FragmentMapBinding
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.ui.base.BaseFragment
import com.example.foulette.util.REQUEST_CODE
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
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import timber.log.Timber

class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map),
    OnMapReadyCallback {
    private val seoul = LatLng(37.5666805, 126.9784147)
    private lateinit var map: GoogleMap
    private lateinit var placesClient: PlacesClient
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var selectedRestaurant : RestaurantResult

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val args: MapFragmentArgs by navArgs()
        //selectedRestaurant = args.restaurant


        Places.initialize(FouletteApplication.ApplicationContext(), BuildConfig.MAPS_API_KEY)
        placesClient = Places.createClient(requireContext())
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Timber.e("${seoul.toString()}")
        googleMap.addMarker(MarkerOptions().position(seoul).title("SEOUL"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(seoul))
    }
    /*override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
    */

}
