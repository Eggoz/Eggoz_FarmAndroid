package com.antino.eggoz.ui.sell_shop.model

import com.antino.eggoz.view.data.LoginUser
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Banner(
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
    @SerializedName("errors")
    @Expose
    var errors: List<LoginUser.Error>?,
    @SerializedName("error_type")
    @Expose
    var errorType: String
) {
    class Result(

        @SerializedName("id")
        @Expose
        var id: Int,
        @SerializedName("created_at")
        @Expose
        var createdAt: String,
        @SerializedName("modified_at")
        @Expose
        var modifiedAt: String,
        @SerializedName("image")
        @Expose
        var image: String,
        @SerializedName("publish_at")
        @Expose
        var publishAt: String,
        @SerializedName("expire_at")
        @Expose
        var expireAt: Any,
        @SerializedName("is_shown")
        @Expose
        var isShown: Boolean
    )
}