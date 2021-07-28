package de.conti.r2d2.kotlindemo.service

import de.conti.r2d2.kotlindemo.MainActivity
import de.conti.r2d2.kotlindemo.model.CityModel
import de.conti.r2d2.kotlindemo.model.WeatherResponse
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.Observable
import okhttp3.logging.HttpLoggingInterceptor

class RXClient {

    private val cacheSize: Int = 10 * 1024 * 1024 // 10 MB
    private var rxService: RXService

    companion object {
        const val BASE_URL: String = "https://api.openweathermap.org/data/2.5/"
        const val API_KEY = "3af8f7d90a7fc2994a1d609b883ebaf0"
    }

    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val cache = Cache(MainActivity.cache, cacheSize.toLong())
        val okHttpClient: OkHttpClient =
            OkHttpClient().newBuilder().addInterceptor(httpLoggingInterceptor).cache(cache).build()
        Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            client(okHttpClient)
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            addConverterFactory(GsonConverterFactory.create())
        }.build().also {
            rxService = it.create(RXService::class.java)
        }

        /*val retrofit: Retrofit =
            Retrofit.Builder().apply {
                baseUrl(BASE_URL)
                client(okHttpClient)
                addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                addConverterFactory(GsonConverterFactory.create())
            }.build()
        rxService = retrofit.create(RXService::class.java)*/
    }

    fun getCityWeather(cityName: String): Observable<CityModel> = rxService.getCityWeather(cityName)

    fun getCurrentLocationWeather(latt: String, longt: String): Observable<WeatherResponse> =
        rxService.getCurrentLocationWeather(latt, longt, API_KEY)

    fun getWeatherForAddedCity(cityName: String): Observable<WeatherResponse> =
        rxService.getWeatherForAddedCity(cityName, API_KEY)
}