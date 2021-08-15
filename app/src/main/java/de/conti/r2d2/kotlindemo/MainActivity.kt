package de.conti.r2d2.kotlindemo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import de.conti.r2d2.kotlindemo.adapter.WeatherListAdapter
import de.conti.r2d2.kotlindemo.databinding.MainLayoutBinding
import de.conti.r2d2.kotlindemo.fragments.BottomSheetDialog
import de.conti.r2d2.kotlindemo.model.WeatherResponse
import de.conti.r2d2.kotlindemo.viewmodel.WeatherViewModel
import java.io.File

class MainActivity : AppCompatActivity(), WeatherListAdapter.OnItemClickListener {


    lateinit var binding: MainLayoutBinding
    lateinit var viewModel: WeatherViewModel

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var currentLatitude: String? = null
    var currentLongitude: String? = null

    companion object {
        lateinit var cache: File
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
            return
        } else {
            binding = MainLayoutBinding.inflate(layoutInflater)
            val view = binding.root
            setContentView(view)
            cache = cacheDir
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

            fusedLocationClient.lastLocation.addOnSuccessListener { lastKnownLocation ->
                currentLatitude = lastKnownLocation.latitude.toString()
                currentLongitude = lastKnownLocation.longitude.toString()
            }

            viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

            viewModel.getCurrentLocationWeather(
                currentLatitude ?: "12.768676",
                currentLongitude ?: "75.207062"
            )

            val divider = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            //val divider = DividerItemDecoration(ContextThemeWrapper(this, R.style.ThemeOverlay_AppCompat), DividerItemDecoration.VERTICAL)

            divider.setDrawable(ContextCompat.getDrawable(this@MainActivity, R.color.white)!!)

            viewModel.getWeatherResponseLiveData().observe(this, Observer { weatherResponse ->
                binding.weatherRecyclerView.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    this.addItemDecoration(divider)
                    adapter = WeatherListAdapter(weatherResponse)
                    (adapter as WeatherListAdapter).setOnItemClickListener(this@MainActivity)
                }

            })


            showToastMessage("another activity")


            val bottomSheetFragment = BottomSheetDialog()

            binding.btnAddNewCity.setOnClickListener {
                bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")
            }

        }
    }

    override fun onItemClick(position: Int, weatherResponse: WeatherResponse) {
        viewModel.formatTime(weatherResponse.sys.sunrise)
        Toast.makeText(this, "Item clicked ${weatherResponse.sys.country}", Toast.LENGTH_LONG)
            .show()
    }
}