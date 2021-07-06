package com.antino.eggoz.ui.sell_shop.model

import androidx.annotation.Keep
import com.antino.eggoz.view.data.LoginUser
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class CatList(
    @SerializedName("error_type")
    @Expose
    var errorType: String,
    @SerializedName("errors")
    @Expose
    var errors: List<LoginUser.Error>?,
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
    var results: List<Result>? = null
) {
    class Result(

        @SerializedName("id")
        @Expose
        var id: Int,
        @SerializedName("name")
        @Expose
        var name: String,
        @SerializedName("description")
        @Expose
        var description: String,
        @SerializedName("image")
        @Expose
        var image: String,
        @SerializedName("code")
        @Expose
        var code: String,
        @SerializedName("is_visible")
        @Expose
        var isVisible: Boolean,
        @SerializedName("hsn")
        @Expose
        var hsn: String
    )
}