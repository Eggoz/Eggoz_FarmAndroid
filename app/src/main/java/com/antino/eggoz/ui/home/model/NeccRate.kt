package com.antino.eggoz.ui.home.model

import androidx.annotation.Keep
import com.antino.eggoz.view.data.LoginUser
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class NeccRate(
    @SerializedName("count")
    @Expose
    var count: Int,
    @SerializedName("next")
    @Expose
    var next: Any,
    @SerializedName("previous")
    @Expose
    var previous: Any,
    @SerializedName("results")
    @Expose
    var results: List<Result>? = null,
    @SerializedName("error_type")
    @Expose
    var errorType: String,
    @SerializedName("errors")
    @Expose
    var errors: List<LoginUser.Error>?
) {
    class Result(
        @SerializedName("id")
        @Expose
        var id: Int,
        @SerializedName("necc_city")
        @Expose
        var neccCity: NeccCity,
        @SerializedName("created_at")
        @Expose
        var createdAt: String,
        @SerializedName("modified_at")
        @Expose
        var modifiedAt: String,
        @SerializedName("currency")
        @Expose
        var currency: String,
        @SerializedName("current_rate")
        @Expose
        var currentRate: String
    ) {
        class NeccCity(
            @SerializedName("id")
            @Expose
            var id: Int,
            @SerializedName("name")
            @Expose
            var name: String,
            @SerializedName("desc")
            @Expose
            var desc: String,
            @SerializedName("zone")
            @Expose
            var zone: Int
        )
    }
}