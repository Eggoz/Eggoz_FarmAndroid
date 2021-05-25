package com.antino.eggoz.view.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class userData(
    @SerializedName("id")
    @Expose
    var id: String,
    @SerializedName("name")
    @Expose
    var name: String, @SerializedName("pic")
    @Expose
    var pic: String, @SerializedName("address")
    @Expose
    var address: String,
    @SerializedName("mobile")
    @Expose
    var mobile: String
)