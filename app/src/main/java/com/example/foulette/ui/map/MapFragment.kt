package com.example.foulette.ui.map

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foulette.BuildConfig
import com.example.foulette.FouletteApplication
import com.example.foulette.R
import com.example.foulette.domain.models.HistoryResult
import com.example.foulette.domain.models.RestaurantResult
import com.example.foulette.domain.models.TmapRouteResult
import com.example.foulette.ui.base.BaseFragment
import com.google.android.gms.common.api.ApiException
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
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.*
import dagger.hilt.android.AndroidEntryPoint
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
        binding.fabTohome.setOnClickListener {
            val toHome = MapFragmentDirections.actionMapFragmentToMainFragment()
            requireView().findNavController().navigate(toHome)
        }
        getPhoto()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        Timber.e("TEST : ON MAP READY")
        this.map = googleMap
        map.isMyLocationEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener {
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        it.latitude, it.longitude
                    ), 15F
                )
            )
        }

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


    fun getPhoto() {
        Timber.e("TEST : GET PHOTO")
        val placeId = selectedRestaurant.id
        val fields = listOf(Place.Field.PHOTO_METADATAS)
        val placeRequest = FetchPlaceRequest.newInstance(placeId, fields)
        placesClient.fetchPlace(placeRequest).addOnSuccessListener { response: FetchPlaceResponse ->
            val place = response.place

            val metada = place.photoMetadatas
            if (metada == null || metada.isEmpty()) {
                return@addOnSuccessListener
            }
            val photoMetadata = metada.first()

            val photoRequest =
                FetchPhotoRequest.builder(photoMetadata).setMaxWidth(500)
                    .setMaxHeight(300)
                    .build()
            placesClient.fetchPhoto(photoRequest)
                .addOnSuccessListener { fetchPhotoResponse: FetchPhotoResponse ->
                    val bitmap = fetchPhotoResponse.bitmap
                    setBottomSheet(bitmap)
                }.addOnFailureListener { exception: Exception ->
                    if (exception is ApiException) {
                        val statusCode = exception.statusCode
                        Timber.e("IMG ERROR!  $statusCode")
                        //TODO("Handle error with given status code.")
                    }
                }

        }
    }

    private fun setBottomSheet(bitmap: Bitmap) {
        binding.btmSheetMap.apply {
            btmSheetImg.setImageBitmap(bitmap)
            btmSheetTitle.text = selectedRestaurant.name
            val distance =
                if (routeData.totalDistance / 1000 > 0) "${routeData.totalDistance / 1000} Km"
                else "${routeData.totalDistance} m"
            btmSheetDistance.text = "예상거리 : 약 $distance"
            btmSheetTime.text = "예상시간 : 약 ${routeData.totalTime / 60} 분"
            val price = listOf("무료", "저렴함", "보통", "조금 비쌈", "매우 비쌈")
            btmSheetPrice.text = "가격대 : ${price[selectedRestaurant.price_level!!]}"
            btmSheetRate.rating = selectedRestaurant.rate!!.toFloat()
        }
        saveHistory(bitmap)
    }

    private fun saveHistory(bitmap: Bitmap) {
        val now = SimpleDateFormat("yyyy-MM-dd kk:mm").format(Date(System.currentTimeMillis()))
        viewModel.saveHistory(
            historyResult = HistoryResult(
                id = 0,
                restaurantName = selectedRestaurant.name!!,
                restaurantImg = bitmap,
                restaurantAddress = selectedRestaurant.address!!,
                date = now,
                restaurantLocLat = selectedRestaurant.latitude!!,
                restaurantLocLog = selectedRestaurant.longitude!!,
                rate = selectedRestaurant.rate!!,
                price = selectedRestaurant.price_level!!
            )
        )
    }

}
