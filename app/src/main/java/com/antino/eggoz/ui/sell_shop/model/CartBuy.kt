package com.antino.eggoz.ui.sell_shop.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CartBuy(
    @SerializedName("error_type")
    @Expose
    var errorTypeval: String? = null,
    @SerializedName("errors")
    @Expose
    var errors: List<Error>? = null,
    @SerializedName("success")
    @Expose
    var success: String? = null


) {
}