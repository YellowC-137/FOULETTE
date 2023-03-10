package yellowc.example.foulette.ui.main

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import yellowc.example.foulette.R
import yellowc.example.foulette.databinding.FragmentMainBinding
import yellowc.example.foulette.domain.models.RestaurantResult
import yellowc.example.foulette.domain.models.TmapRouteResult
import yellowc.example.foulette.ui.base.BaseFragment
import yellowc.example.foulette.ui.roulette.RouletteDialog
import yellowc.example.foulette.ui.roulette.RouletteState
import yellowc.example.foulette.util.REQUEST_CODE
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

    /*
    private fun chromedriver() {
        // WebDriverManager??? ???????????? ChromeDriver ?????? ??? ?????? ??????
        WebDriverManager.chromedriver().setup()

        // Chrome ?????? ??????
        val options = ChromeOptions()
        options.addArguments("--disable-gpu")
        options.addArguments("--no-sandbox")
        options.addArguments("--disable-dev-shm-usage")

        // ChromeDriver ?????? ??????
        val driver: WebDriver = ChromeDriver(options)

        // ????????? ?????? ???????????? ??????
        driver.get("https://map.naver.com/v5/?c=13.63,0,0,0,dh&isCorrectAnswer=true")

        // ???????????? ????????? ?????? ??? ?????? ?????? ??????
        val searchBox: WebElement = driver.findElement(By.className("input_search"))
        searchBox.sendKeys("????????? ????????????")
        searchBox.submit()

        // ?????? ?????? ??????
        val restaurantLink: WebElement =
            driver.findElement(By.xpath("//a[contains(@href,'place') and contains(@href,'menu')][1]"))
        restaurantLink.click()

        // ?????? ?????? ??????
        val menuSection: WebElement =
            driver.findElement(By.xpath("//section[contains(@class,'_3mNZz') and contains(@class,'_1aA5j')]"))
        val menuItems: List<WebElement> = menuSection.findElements(By.tagName("li"))
        for (menuItem in menuItems) {
            val name: String = (menuItem.findElement(By.tagName("strong")) as WebElement).text
            val price: String = (menuItem.findElement(By.tagName("em")) as WebElement).text
            Timber.e("????????? ??????:$name: ??????:$price")
        }

        // WebDriver ??????
        driver.quit()
    }

    private fun selenium() = runBlocking {
        withContext(Dispatchers.IO) {
            val driverPath =
                "C:/Users/zdrgn/AppData/Local/chrome_driver/chromedriver_win32/chromedriver.exe"
            System.setProperty("webdriver.chrome.driver", driverPath)
            val driver: WebDriver = ChromeDriver()
            driver.get("https://map.naver.com/v5/?c=13.63,0,0,0,dh&isCorrectAnswer=true")
            val searchBox: WebElement = driver.findElement(By.className("input_search"))
            searchBox.sendKeys("????????? ????????????")
            searchBox.submit()
            val restaurantLink: WebElement =
                driver.findElement(By.xpath("//a[contains(@href,'place') and contains(@href,'menu')][1]"))
            restaurantLink.click()
            val menuSection: WebElement =
                driver.findElement(By.xpath("//section[contains(@class,'_3mNZz') and contains(@class,'_1aA5j')]"))
            val menuItems: List<WebElement> = menuSection.findElements(By.tagName("li"))
            for (menuItem in menuItems) {
                val name: String = menuItem.findElement(By.tagName("strong")).text
                val price: String = menuItem.findElement(By.tagName("em")).text
                Timber.e("????????? ??????:$name: ??????:$price")
            }
            driver.quit()
        }
    }
     */

    //TODO ?????? ??????
    private fun initView() {
        binding.apply {
            btnSearchFromMylocation.setOnClickListener {
                progress.visibility = View.VISIBLE
                val animator = ObjectAnimator.ofInt(progress, "progress", 0, 100)
                animator.duration = 2000 // 2???
                animator.interpolator = LinearInterpolator()
                animator.start()
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
            //TODO ??????
        } else {
            EasyPermissions.requestPermissions(
                host = this,
                rationale = "PERMISSIONS",
                requestCode = REQUEST_CODE,
                perms = perms
            )
            Snackbar.make(requireView(), "????????? ???????????????.", Snackbar.LENGTH_LONG).show()
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
                            "??? ??????",
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
                    if (it.isNotEmpty()) {
                        for (routes in it) {
                            route = routes
                        }
                        playRoulette()
                    }
                }
            }
        }
    }

    private fun playRoulette() {
        binding.progress.visibility = View.GONE
        viewModel.setRouletteState(RouletteState.playing)
        RouletteDialog().show(
            requireActivity().supportFragmentManager,
            "Roulette"
        )
        val toMap = MainFragmentDirections.actionMainFragmentToMapFragment(result, route)
        requireView().findNavController().navigate(toMap)
    }

}



