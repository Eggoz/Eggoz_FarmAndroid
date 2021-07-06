package com.antino.eggoz.ui.profile.Model

import androidx.annotation.Keep
import com.antino.eggoz.view.data.LoginUser
import com.antino.eggoz.view.data.Signup2
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class FarmerProfile(
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("farmer")
    @Expose
    var farmer: FarmerInfo,

    @SerializedName("necc_zone")
    @Expose
    var neccZone: Int?=null,

    @SerializedName("error_type")
    @Expose
    var errorType: String,

    @SerializedName("errors")
    @Expose
    var errors: List<LoginUser.Error>?,

    @SerializedName("farmer_iot_id")
    @Expose
    var farmer_iot_id: String
) {

    class FarmerInfo(
        @SerializedName("id")
        @Expose
        var id: Int,
        @SerializedName("name")
        @Expose
        var name: String,
        @SerializedName("email")
        @Expose
        var email: String,
        @SerializedName("phone_no")
        @Expose
        var phoneNo: String?=null,
        @SerializedName("default_address")
        @Expose
        var defaultAddress: Defaultaddress?=null,
        @SerializedName("addresses")
        @Expose
        var addresses: List<Address>? = null,
        @SerializedName("image")
        @Expose
        var image: String
    ) {

        class Address(

            @SerializedName("id")
            @Expose
            var id: Int,
            @SerializedName("default_address_user")
            @Expose
            var defaultAddressUser: List<Integer>? = null,
            @SerializedName("user_addresses_user")
            @Expose
            var userAddressesUser: List<Integer>? = null,
            @SerializedName("address_name")
            @Expose
            var addressName: String,
            @SerializedName("building_address")
            @Expose
            var buildingAddress: String,
            @SerializedName("street_address")
            @Expose
            var streetAddress: String,
            @SerializedName("city")
            @Expose
            var city: Any,
            @SerializedName("landmark")
            @Expose
            var landmark: String,
            @SerializedName("pinCode")
            @Expose
            var pinCode: Int,
            @SerializedName("latitude")
            @Expose
            var latitude: Any,
            @SerializedName("longitude")
            @Expose
            var longitude: Any,
            @SerializedName("date_added")
            @Expose
            var dateAdded: String,
            @SerializedName("billing_city")
            @Expose
            var billingCity: String
        )

        class Defaultaddress(

            @SerializedName("id")
            @Expose
            var id: Int,
            @SerializedName("default_address_user")
            @Expose
            var defaultAddressUser: List<Integer>? = null,
            @SerializedName("user_addresses_user")
            @Expose
            var userAddressesUser: List<Integer>? = null,
            @SerializedName("address_name")
            @Expose
            var addressName: String,
            @SerializedName("building_address")
            @Expose
            var buildingAddress: String,
            @SerializedName("street_address")
            @Expose
            var streetAddress: String,
            @SerializedName("city")
            @Expose
            var city: Any,
            @SerializedName("landmark")
            @Expose
            var landmark: String,
            @SerializedName("pinCode")
            @Expose
            var pinCode: Int?=-1,
            @SerializedName("latitude")
            @Expose
            var latitude: Any,
            @SerializedName("longitude")
            @Expose
            var longitude: Any,
            @SerializedName("date_added")
            @Expose
            var dateAdded: String,
            @SerializedName("billing_city")
            @Expose
            var billingCity: String
        ) {
        }
    }
}