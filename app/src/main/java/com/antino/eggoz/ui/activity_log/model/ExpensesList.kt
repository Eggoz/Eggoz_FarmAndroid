package com.antino.eggoz.ui.activity_log.model

import com.antino.eggoz.view.data.LoginUser
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ExpensesList(
    @SerializedName("count")
    @Expose
    var count: Int,
    @SerializedName("next")
    @Expose
    var next: String,
    @SerializedName("previous")
    @Expose
    var previous: String,
    @SerializedName("results")
    @Expose
    var results: List<ExpensesData>,
    @SerializedName("errors")
    @Expose
    var errors: List<LoginUser.Error>?,
    @SerializedName("error_type")
    @Expose
    var errorType: String,
    @SerializedName("heading")
    @Expose
    var heading: String,
    @SerializedName("description")
    @Expose
    var description: String,
    @SerializedName("author")
    @Expose
    var author: String
) {
    class ExpensesData(
        @SerializedName("id")
        @Expose
        var id: Int,
        @SerializedName("date")
        @Expose
        var date: String,
        @SerializedName("quantity")
        @Expose
        var quantity: Int,
        @SerializedName("amount")
        @Expose
        var amount: Double,
        @SerializedName("remark")
        @Expose
        var remark: String,
        @SerializedName("farmer")
        @Expose
        var farmer: Int,
        @SerializedName("party")
        @Expose
        var party: Int,
        @SerializedName("productSubDivision")
        @Expose
        var productSubDivision: Int
    )
}