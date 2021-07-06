package com.antino.eggoz.ui.sell_shop.model

import androidx.annotation.Keep
import com.antino.eggoz.view.data.LoginUser
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class ItemList(
    @SerializedName("error_type")
    @Expose
    var errorType: String,
    @SerializedName("errors")
    @Expose
    var errors: List<LoginUser.Error>?,
    @SerializedName("count")
    @Expose
    var countval: Int,
    @SerializedName("next")
    @Expose
    var next: String,
    @SerializedName("previous")
    @Expose
    var previous: String,
    @SerializedName("results")
    @Expose
    var results: List<ItemResult>? = null
) {
    class ItemResult(
        @SerializedName("id")
        @Expose
        var id: Int,
        @SerializedName("feedProductDivision")
        @Expose
        var feedProductDivision: FeedProductDivision,
        @SerializedName("feedProductSubDivision")
        @Expose
        var feedProductSubDivision: FeedProductSubDivision,
        @SerializedName("vendor")
        @Expose
        var vendor: Vendor,
        @SerializedName("feedProductSpecifications")
        @Expose
        var feedProductSpecifications: List<FeedProductSpecification>? = null,
        @SerializedName("name")
        @Expose
        var name: String,
        @SerializedName("description")
        @Expose
        var description: String,
        @SerializedName("slug")
        @Expose
        var slug: String,
        @SerializedName("is_available")
        @Expose
        var isAvailable: Boolean,
        @SerializedName("currency")
        @Expose
        var currency: String,
        @SerializedName("feed_product_image")
        @Expose
        var feed_product_image:String,
        @SerializedName("current_price")
        @Expose
        var currentPrice: String,
        @SerializedName("updated_at")
        @Expose
        var updatedAt: String,
        @SerializedName("charge_taxes")
        @Expose
        var chargeTaxes: Boolean
    ) {
        class FeedProductDivision(
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
            var isVisible: Boolean,
            @SerializedName("hsn")
            @Expose
            var hsn: String
        )

        class FeedProductSpecification(
            @SerializedName("id")
            @Expose
            var id: Int,
            @SerializedName("specification")
            @Expose
            var specification: String,
            @SerializedName("feedProduct")
            @Expose
            var feedProduct: Int
        )

        class FeedProductSubDivision(
            @SerializedName("id")
            @Expose
            var id: Int,
            @SerializedName("name")
            @Expose
            var name: String,
            @SerializedName("description")
            @Expose
            var description: String,
            @SerializedName("is_visible")
            @Expose
            var isVisible: Boolean,
            @SerializedName("code")
            @Expose
            var code: String,
            @SerializedName("feedProductDivision")
            @Expose
            var feedProductDivision: Integer
        )

        class Vendor(

            @SerializedName("id")
            @Expose
            var id: Int,
            @SerializedName("name")
            @Expose
            var name: String
        )
    }
}