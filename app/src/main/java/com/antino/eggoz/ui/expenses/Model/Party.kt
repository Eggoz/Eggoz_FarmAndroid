package com.antino.eggoz.ui.expenses.Model

import com.antino.eggoz.view.data.LoginUser
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Party(
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
    var results: List<PartList>) {
    class PartList(
        @SerializedName("id")
        @Expose
        var id: Int,
        @SerializedName("name")
        @Expose
        var name: String)
}