package com.antino.eggoz.ui.feed.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class FeedData(
    @SerializedName("count")
    @Expose
    var count: Int? = null,

    @SerializedName("next")
    @Expose
    var next: String? = null,
    @SerializedName("previous")
    @Expose
    var previous: String? = null,
    @SerializedName("results")
    @Expose
    var results: List<Result>? = null
) {
    class Result(

        @SerializedName("id")
        @Expose
        var id: Int,
        @SerializedName("count")
        @Expose
        var count: Int? = null,
        @SerializedName("heading")
        @Expose
        var heading: String,
        @SerializedName("description")
        @Expose
        var description: String,
        @SerializedName("author")
        @Expose
        var author: Author,
        @SerializedName("publish_at")
        @Expose
        var publishAt: String,
        @SerializedName("expire_at")
        @Expose
        var expireAt: String,
        @SerializedName("is_pinned")
        @Expose
        var isPinned: Boolean,
        @SerializedName("images")
        @Expose
        var images: List<Image>? = null,
        @SerializedName("is_liked")
        @Expose
        var isLiked: Boolean,
        @SerializedName("stats")
        @Expose
        var stats: Stats
    ) {
        class Author(

            @SerializedName("id")
            @Expose
            var id: Int,
            @SerializedName("email")
            @Expose
            var email: String,
            @SerializedName("name")
            @Expose
            var name: String,
            @SerializedName("phone_no")
            @Expose
            var phoneNo: String,
            @SerializedName("image")
            @Expose
            var image: String

        )

        class Stats(
            @SerializedName("likes")
            @Expose
            var likes: Int,
            @SerializedName("comments")
            @Expose
            var comments: Int
        )

        class Image(
            @SerializedName("id")
            @Expose
            var id: Int,
            @SerializedName("image")
            @Expose
            var image: String,
            @SerializedName("image_order")
            @Expose
            var imageOrder: Int
        )

    }
}