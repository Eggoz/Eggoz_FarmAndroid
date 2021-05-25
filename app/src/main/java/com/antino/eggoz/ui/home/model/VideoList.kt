package com.antino.eggoz.ui.home.model

import com.antino.eggoz.view.data.LoginUser
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VideoList(
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
        @SerializedName("video_tags")
        @Expose
        var videoTags: List<Any>? = null,
        @SerializedName("video_url")
        @Expose
        var videoUrl: String,
        @SerializedName("image_url")
        @Expose
        var imageUrl: String,
        @SerializedName("description")
        @Expose
        var description: String,
        @SerializedName("videoCategory")
        @Expose
        var videoCategory: Int
    )
}