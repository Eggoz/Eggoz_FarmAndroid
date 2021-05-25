package com.antino.eggoz.ui.feed.model

import com.antino.eggoz.view.data.LoginUser
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Comment(
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
    var results: List<ResultComment>? = null,
    @SerializedName("errors")
    @Expose
    var errors: List<LoginUser.Error>?,
    @SerializedName("error_type")
    @Expose
    var errorType: String,
    @SerializedName("success")
    @Expose
    var success: String? = null
) {
    class ResultComment(

        @SerializedName("id")
        @Expose
        var id: Int,
        @SerializedName("is_liked")
        @Expose
        var isLiked: Boolean,
        @SerializedName("stats")
        @Expose
        var stats: Stats,
        @SerializedName("created_at")
        @Expose
        var createdAt: String,
        @SerializedName("modified_at")
        @Expose
        var modifiedAt: String,
        @SerializedName("comment_text")
        @Expose
        var commentText: String,
        @SerializedName("is_active")
        @Expose
        var isActive: Boolean,
        @SerializedName("is_pinned")
        @Expose
        var isPinned: Boolean,
        @SerializedName("user")
        @Expose
        var user: User,
        @SerializedName("post")
        @Expose
        var post: Int,
        @SerializedName("parent_comment")
        @Expose
        var parentComment: Any
    ) {
        class User(
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
    }
}