package de.conti.r2d2.kotlindemo.model


data class CityModel(
    var title: String,
    var location_type: String,
    var woeid: String,
    var latt_long: String
)