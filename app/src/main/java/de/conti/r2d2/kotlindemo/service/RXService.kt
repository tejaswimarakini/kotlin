package de.conti.r2d2.kotlindemo.service

import de.conti.r2d2.kotlindemo.model.CityModel
import de.conti.r2d2.kotlindemo.model.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RXService {

    // meta weather API
    @GET("/api/location/search")
    fun getCityWeather(@Query("query") key: String): Observable<CityModel>

    /*@GET("/api/location/search")
    fun getCurrentLocationWeather(@Query("lattlong") latlong: String): Observable<CityModel>*/

    /* Sample URL

    https://api.openweathermap.org/data/2.5/weather?lat=12.768676&lon=75.207062&appid=3af8f7d90a7fc2994a1d609b883ebaf0

    */

    /*companion object {
        const val API_KEY = "3af8f7d90a7fc2994a1d609b883ebaf0"
    }*/

    @GET("weather")
    fun getCurrentLocationWeather(
        @Query("lat") lat: String,
        @Query("lon") lng: String,
        @Query("appid") appId: String
    ): Observable<WeatherResponse>

    @GET("weather")
    fun getWeatherForAddedCity(
        @Query("q") cityName: String,
        @Query("appid") appId: String
    ): Observable<WeatherResponse>

}