package de.conti.r2d2.kotlindemo.service

import android.util.Log
import androidx.lifecycle.MutableLiveData
import de.conti.r2d2.kotlindemo.model.CityModel
import de.conti.r2d2.kotlindemo.model.WeatherResponse
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class WeatherRepository {

    var rxClient: RXClient = RXClient()

    companion object {
        val TAG = WeatherRepository::class.java.name
        var weatherResponseList: ArrayList<WeatherResponse> = ArrayList()
    }

    /*fun getCityWeatherDetails(cityName: String): MutableLiveData<CityModel> {
        var liveData: MutableLiveData<CityModel> = MutableLiveData()
        rxClient.getCityWeather(cityName).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                object : Observer<CityModel?> {
                    override fun onNext(cityModelResponse: CityModel) {
                        // cityModel = cityModelResponse
                    }

                    override fun onError(e: Throwable) {
                        TODO("Not yet implemented")
                    }

                    override fun onComplete() {
                        //  liveData.postValue(cityModel)
                    }

                    override fun onSubscribe(d: Disposable) {
                        TODO("Not yet implemented")
                    }
                })

        return liveData
    }*/

    fun getCurrentLocationWeather(
        latt: String,
        longt: String,
        liveData: MutableLiveData<ArrayList<WeatherResponse>>
    ) {
        rxClient.getCurrentLocationWeather(latt, longt).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<WeatherResponse> {

                override fun onComplete() {}

                override fun onNext(weatherResponseObj: WeatherResponse) {
                    weatherResponseList.add(weatherResponseObj)
                    liveData.postValue(weatherResponseList)
                }

                override fun onError(e: Throwable) {
                    Log.e(
                        TAG,
                        "Exception occurred while fetching weather for current location ${e.message}"
                    )
                }

                override fun onSubscribe(d: Disposable) {}
            })

    }

    fun getWeatherForAddedCity(
        cityName: String,
        liveData: MutableLiveData<ArrayList<WeatherResponse>>
    ) {
        rxClient.run {
            getWeatherForAddedCity(cityName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<WeatherResponse> {

                    override fun onComplete() = Unit

                    override fun onNext(weatherResponseObj: WeatherResponse) {
                        weatherResponseList.add(weatherResponseObj)
                        liveData.postValue(weatherResponseList)
                    }

                    override fun onError(e: Throwable) {
                        Log.e(
                            TAG,
                            "Exception occurred while fetching weather for current location ${e.message}"
                        )
                    }

                    override fun onSubscribe(d: Disposable) = Unit
                })
        }
    }


    /*val observer: Observer<WeatherResponse> = object : Observer<WeatherResponse> {
        override fun onComplete() {
            println("Completed")
        }

        override fun onNext(weatherResponse: WeatherResponse) {
            println("Received-> $weatherResponse")
        }

        override fun onError(e: Throwable) {
            println("Error Occured => ${e.message}")
        }

        override fun onSubscribe(d: Disposable) {
            println("Subscription")
        }
    }*/


    /*Observable.create<String> { emitter ->
            emitter.onNext("Hello, World!")
            emitter.onComplete()
        }
            .subscribeBy(
                onNext     = { s -> println(s) },
                onError    = { e -> println(e) },
                onComplete = {      println("onComplete") }
            )*/

    /*val list = listOf("Alpha", "Beta", "Gamma", "Delta", "Epsilon")

    list.toObservable() // extension function for Iterables
        .filter { it.length >= 5 }
        .subscribeBy(  // named arguments for lambda Subscribers
            onNext = { println(it) },
            onError =  { it.printStackTrace() },
            onComplete = { println("Done!") }
        )*/


}