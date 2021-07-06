package com.antino.eggoz.ui.home.model

import androidx.annotation.Keep
import com.antino.eggoz.view.data.LoginUser
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class WeatherData(

    @SerializedName("results")
    @Expose
    var results: Results,
    @SerializedName("error_type")
    @Expose
    var errorType: String,
    @SerializedName("errors")
    @Expose
    var errors: List<LoginUser.Error>?,
    @SerializedName("cod")
    @Expose
    var cod: String,
    @SerializedName("message")
    @Expose
    var message: String

) {

    class Results(
        @SerializedName("coord")
        @Expose
        var coord: Coord,
        @SerializedName("weather")
        @Expose
        var weather: List<Weather>? = null,
        @SerializedName("base")
        @Expose
        var base: String,
        @SerializedName("main")
        @Expose
        var main: Main,
        @SerializedName("visibility")
        @Expose
        var visibility: Int,
        @SerializedName("wind")
        @Expose
        var wind: Wind,
        @SerializedName("clouds")
        @Expose
        var clouds: Clouds,
        @SerializedName("dt")
        @Expose
        var dt: Int,
        @SerializedName("sys")
        @Expose
        var sys: Sys,
        @SerializedName("timezone")
        @Expose
        var timezone: Int,
        @SerializedName("id")
        @Expose
        var id: Int,
        @SerializedName("name")
        @Expose
        var name: String,
        @SerializedName("cod")
        @Expose
        var cod: Int
    ) {
        class Wind(
            @SerializedName("speed")
            @Expose
            var speed: Double,
            @SerializedName("deg")
            @Expose
            var deg: Int,
            @SerializedName("gust")
            @Expose
            var gust: Double
        )

        class Sys(
            @SerializedName("country")
            @Expose
            var country: String,
            @SerializedName("sunrise")
            @Expose
            var sunrise: Int,
            @SerializedName("sunset")
            @Expose
            var sunset: Int
        )

        class Coord(
            @SerializedName("lon")
            @Expose
            var lon: Double,
            @SerializedName("lat")
            @Expose
            var lat: Double
        )

        class Main(
            @SerializedName("temp")
            @Expose
            var temp: Double,
            @SerializedName("feels_like")
            @Expose
            var feelsLike: Double,
            @SerializedName("temp_min")
            @Expose
            var tempMin: Double,
            @SerializedName("temp_max")
            @Expose
            var tempMax: Double,
            @SerializedName("pressure")
            @Expose
            var pressure: Int,
            @SerializedName("humidity")
            @Expose
            var humidity: Int,
            @SerializedName("sea_level")
            @Expose
            var seaLevel: Int,
            @SerializedName("grnd_level")
            @Expose
            var grndLevel: Int
        )

        class Clouds(
            @SerializedName("all")
            @Expose
            var all: Int
        )

        class Weather(
            @SerializedName("id")
            @Expose
            var id: Int,
            @SerializedName("main")
            @Expose
            var main: String,
            @SerializedName("description")
            @Expose
            var description: String,
            @SerializedName("icon")
            @Expose
            var icon: String
        )
    }
}