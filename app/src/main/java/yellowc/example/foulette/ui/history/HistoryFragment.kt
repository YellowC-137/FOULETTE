package yellowc.example.foulette.ui.history

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import yellowc.example.foulette.R
import yellowc.example.foulette.databinding.FragmentHistoryBinding
import yellowc.example.foulette.domain.models.RestaurantResult
import yellowc.example.foulette.domain.models.TmapRouteResult
import yellowc.example.foulette.ui.base.BaseFragment

@SuppressLint("MissingPermission")
@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(R.layout.fragment_history) {
    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var route: TmapRouteResult
    private lateinit var myLoc: Location
    private lateinit var selectedRestaurant: RestaurantResult
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val pagingAdapter: HistoryAdapter by lazy {
        HistoryAdapter(
            deleteClicked = {
                viewModel.deleteHistoryById(it.id)
                Snackbar.make(binding.root, "삭제되었습니다.", Snackbar.LENGTH_SHORT).show()
            },
            itemClicked = {
                selectedRestaurant = RestaurantResult(
                    id = it.placeId,
                    price_level = it.price,
                    name = it.restaurantName,
                    type = null,
                    latitude = it.restaurantLocLat,
                    longitude = it.restaurantLocLog,
                    rate = it.rate,
                    ImgUrl = "",//it.restaurantImg
                    address = it.restaurantAddress,
                    photos = null
                )
                getLoc()
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        initRecyclerView()
        collectFlow()
        viewModel.getAllHistory()
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.historyData.collectLatest { it ->
                    pagingAdapter.submitData(it)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLoc() {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            myLoc = it
            viewModel.getRoute(
                myLoc.longitude,
                myLoc.latitude,
                selectedRestaurant.longitude!!.toDouble(),
                selectedRestaurant.latitude!!.toDouble(),
                "내 위치",
                selectedRestaurant.name!!
            )
            getRoute()
        }
    }

    private fun getRoute() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.routesData.collectLatest {
                    if (it.isNotEmpty()) {
                        for (routes in it) {
                            route = routes
                        }
                        val toMap =
                            yellowc.example.foulette.ui.history.HistoryFragmentDirections.actionHistoryFragmentToMapFragment(
                                selectedRestaurant,
                                route
                            )
                        requireView().findNavController().navigate(toMap)
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            rcvHistory.adapter = pagingAdapter
        }
    }

}