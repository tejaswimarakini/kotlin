package de.conti.r2d2.kotlindemo.viewmodel

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.conti.r2d2.kotlindemo.model.WeatherResponse
import de.conti.r2d2.kotlindemo.service.WeatherRepository
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import java.util.concurrent.TimeUnit


class WeatherViewModel : ViewModel() {

    private var liveData: MutableLiveData<ArrayList<WeatherResponse>>
    private var weatherRepository: WeatherRepository = WeatherRepository()

    init {
        liveData = MutableLiveData()
    }


    fun getCurrentLocationWeather(currentLatitude: String, currentLongitude: String) {

        weatherRepository.getCurrentLocationWeather(currentLatitude, currentLongitude, liveData)
    }

    fun getWeatherResponseLiveData(): MutableLiveData<ArrayList<WeatherResponse>> {
        return liveData
    }

    fun getWeatherForAddedCity(cityName: String) {
        weatherRepository.getWeatherForAddedCity(cityName, liveData)
    }

    @SuppressLint("SimpleDateFormat")
    fun formatTime(time: Long) : String {
        val date = Date(time*1000L)
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm a")
        return formatter.format(date)
    }
}