package com.antino.eggoz.ui.home.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class IotData(

    @SerializedName("welcome")
    @Expose
    var welcome: String,
    @SerializedName("connected")
    @Expose
    var connected: Int,
    @SerializedName("epoch")
    @Expose
    var epochval: Int,
    @SerializedName("temperature")
    @Expose
    var temperatureval: Double,
    @SerializedName("humidity")
    @Expose
    var humidityval: Double,
    @SerializedName("NH3")
    @Expose
    var nh3val: Double,
    @SerializedName("Vcc")
    @Expose
    var vccval: Double,
    @SerializedName("version")
    @Expose
    var versionval: String,
    @SerializedName("rssi")
    @Expose
    var rssival: Double,
    @SerializedName("ssid")
    @Expose
    var ssid: String,
    @SerializedName("ID")
    @Expose
    var id: String,
    @SerializedName("NH3_V")
    @Expose
    var nh3V: Double,
    @SerializedName("message")
    @Expose
    var message: String?=null
) {
}