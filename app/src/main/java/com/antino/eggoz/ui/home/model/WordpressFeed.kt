package com.antino.eggoz.ui.home.model

import com.antino.eggoz.view.data.LoginUser
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WordpressFeed(
    @SerializedName("error_type")
    @Expose
    var errorType: String,
    @SerializedName("errors")
    @Expose
    var errors: List<LoginUser.Error>?,

    @SerializedName("results")
    @Expose
    var results: Resultss
) {
    class Resultss(
        @SerializedName("results")
        @Expose
        var results: List<Result>? = null
    ) {

        class Result(
            @SerializedName("id")
            @Expose
            var id: Int,
            @SerializedName("link")
            @Expose
            var link: String,
            @SerializedName("title")
            @Expose
            var title: String,
            @SerializedName("description")
            @Expose
            var description: String,
            @SerializedName("image_url")
            @Expose
            var image_url: String

        )
    }
}