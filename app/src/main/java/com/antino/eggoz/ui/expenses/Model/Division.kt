package com.antino.eggoz.ui.expenses.Model

import androidx.annotation.Keep
import com.antino.eggoz.view.data.LoginUser
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class Division(
    @SerializedName("count")
    @Expose
    var count: Int,
    @SerializedName("next")
    @Expose
    var next: String,
    @SerializedName("previous")
    @Expose
    var previous: String,
    @SerializedName("errors")
    @Expose
    var errors: List<LoginUser.Error>?,
    @SerializedName("error_type")
    @Expose
    var errorType: String,
    @SerializedName("results")
    @Expose
    var results: List<DivisionList>) {
    class DivisionList(
        @SerializedName("id")
        @Expose
        var id: Int,
        @SerializedName("name")
        @Expose
        var name: String,
        @SerializedName("description")
        @Expose
        var description: String,
        @SerializedName("code")
        @Expose
        var code: String,
        @SerializedName("is_visible")
        @Expose
        var is_visible: Boolean,
        @SerializedName("hsn")
        @Expose
        var hsn: String

        )
}