package com.antino.eggoz.ui.feed.model

import androidx.annotation.Keep
import com.antino.eggoz.view.data.LoginUser
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class PostComment(
    @SerializedName("post")
    @Expose
    var post: Int,
    @SerializedName("comment_text")
    @Expose
    var commentTextval: String,
    @SerializedName("is_pinned")
    @Expose
    var isPinned: Boolean,
    @SerializedName("parent_comment")
    @Expose
    var parentComment: Any,
    @SerializedName("errors")
    @Expose
    var errors: List<LoginUser.Error>?,
    @SerializedName("error_type")
    @Expose
    var errorType: String
) {
}