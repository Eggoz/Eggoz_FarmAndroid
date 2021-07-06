package com.antino.eggoz.ui.home.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class WeatherDataByCode(
    @SerializedName("coord")
    @Expose
    val coord: Coord? = null,

    @SerializedName("weather")
    @Expose
    val weather: List<Weather>? = null,

    @SerializedName("base")
    @Expose
    val base: String? = null,

    @SerializedName("main")
    @Expose
    val main: Main? = null,

    @SerializedName("visibility")
    @Expose
    val visibility: Int? = null,

    @SerializedName("wind")
    @Expose
    val wind: Wind? = null,

    @SerializedName("clouds")
    @Expose
    val clouds: Clouds? = null,

    @SerializedName("dt")
    @Expose
    val dt: Int? = null,

    @SerializedName("sys")
    @Expose
    val sys: Sys? = null,

    @SerializedName("timezone")
    @Expose
    val timezone: Int? = null,

    @SerializedName("id")
    @Expose
    val id: Int? = null,

    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("cod")
    @Expose
    val cod: Int? = null
) {

    class Clouds(
        @SerializedName("all")
        @Expose
        val all: Int? = null
    )


    class Coord(
        @SerializedName("lon")
        @Expose
        val lon: Double? = null,

        @SerializedName("lat")
        @Expose
        val lat: Double? = null
    )

    class Main(
        @SerializedName("temp")
        @Expose
        val temp: Double? = null,

        @SerializedName("feels_like")
        @Expose
        val feelsLike: Double? = null,

        @SerializedName("temp_min")
        @Expose
        val tempMin: Double? = null,

        @SerializedName("temp_max")
        @Expose
        val tempMax: Double? = null,

        @SerializedName("pressure")
        @Expose
        val pressure: Int? = null,

        @SerializedName("humidity")
        @Expose
        val humidity: Int? = null
    )

    class Sys(
        @SerializedName("type")
        @Expose
        val type: Int? = null,

        @SerializedName("id")
        @Expose
        val id: Int? = null,

        @SerializedName("country")
        @Expose
        val country: String? = null,

        @SerializedName("sunrise")
        @Expose
        val sunrise: Int? = null,

        @SerializedName("sunset")
        @Expose
        val sunset: Int? = null
    )

    class Weather(
        @SerializedName("id")
        @Expose
        val id: Int? = null,

        @SerializedName("main")
        @Expose
        val main: String? = null,

        @SerializedName("description")
        @Expose
        val description: String? = null,

        @SerializedName("icon")
        @Expose
        val icon: String? = null
    )

    class Wind(

        @SerializedName("speed")
        @Expose
        val speed: Double? = null,

        @SerializedName("deg")
        @Expose
        val deg: Int? = null,

        @SerializedName("gust")
        @Expose
        val gust: Double? = null
    )
}
