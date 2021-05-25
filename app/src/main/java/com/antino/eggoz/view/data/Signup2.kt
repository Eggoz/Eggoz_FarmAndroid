package com.antino.eggoz.view.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Signup2(
    @SerializedName("success")
    @Expose
    var success: String,
    @SerializedName("token")
    @Expose
    var token: String,
    @SerializedName("user")
    @Expose
    var user: Userdata,
    @SerializedName("errors")
    @Expose
    var errors: List<LoginUser.Error>?,
    @SerializedName("error_type")
    @Expose
    var errorType: String
) {
    class Userdata(
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
        var phone_no: String,
        @SerializedName("default_address")
        @Expose
        var default_address: DefaultAddress,
        @SerializedName("addresses")
        @Expose
        var addresses: List<Address>,
        @SerializedName("userProfile")
        @Expose
        var userProfile: UserProfile,
        @SerializedName("userData")
        @Expose
        var userData: String,
        @SerializedName("userCities")
        @Expose
        var userCities: List<UserCity>,
        @SerializedName("date_joined")
        @Expose
        var date_joined: String
    ) {
        class DefaultAddress(
            @SerializedName("id")
            @Expose
            var id: Int,
            @SerializedName("default_address_user")
            @Expose
            var default_address_user: List<Int>,
            @SerializedName("user_addresses_user")
            @Expose
            var user_addresses_user: List<Int>,
            @SerializedName("address_name")
            @Expose
            var address_name: String,
            @SerializedName("building_address")
            @Expose
            var building_address: String,
            @SerializedName("street_address")
            @Expose
            var street_address: String,
            @SerializedName("city")
            @Expose
            var city: City,
            @SerializedName("landmark")
            @Expose
            var landmark: String,
            @SerializedName("pinCode")
            @Expose
            var pinCode: Int,
            @SerializedName("latitude")
            @Expose
            var latitude: String,
            @SerializedName("longitude")
            @Expose
            var longitude: String,
            @SerializedName("date_added")
            @Expose
            var date_added: String,
            @SerializedName("billing_city")
            @Expose
            var billing_city: String

        ) {
            class City(
                @SerializedName("id")
                @Expose
                var id: Int,
                @SerializedName("city_name")
                @Expose
                var city_name: String,
                @SerializedName("state")
                @Expose
                var state: String,
                @SerializedName("country")
                @Expose
                var country: String
            )
        }

        class Address(
            @SerializedName("id")
            @Expose
            var id: Int,
            @SerializedName("default_address_user")
            @Expose
            var default_address_user: List<Int>,
            @SerializedName("user_addresses_user")
            @Expose
            var user_addresses_user: List<Int>,
            @SerializedName("address_name")
            @Expose
            var address_name: String,
            @SerializedName("building_address")
            @Expose
            var building_address: String,
            @SerializedName("street_address")
            @Expose
            var street_address: String,
            @SerializedName("city")
            @Expose
            var city: City__1,
            @SerializedName("landmark")
            @Expose
            var landmark: String,
            @SerializedName("pinCode")
            @Expose
            var pinCode: Int,
            @SerializedName("latitude")
            @Expose
            var latitude: String,
            @SerializedName("longitude")
            @Expose
            var longitude: String,
            @SerializedName("date_added")
            @Expose
            var date_added: String,
            @SerializedName("billing_city")
            @Expose
            var billing_city: String


        ) {
            class City__1(
                @SerializedName("id")
                @Expose
                var id: Int,
                @SerializedName("city_name")
                @Expose
                var city_name: String,
                @SerializedName("state")
                @Expose
                var state: String,
                @SerializedName("country")
                @Expose
                var country: String
            )
        }

        class UserCity(
            @SerializedName("id")
            @Expose
            var id: Int,
            @SerializedName("city_name")
            @Expose
            var city_name: String,
            @SerializedName("state")
            @Expose
            var state: String,
            @SerializedName("country")
            @Expose
            var country: String
        )

        class UserProfile(
            @SerializedName("id")
            @Expose
            var id: Int,
            @SerializedName("departments")
            @Expose
            var departments: List<String>,
            @SerializedName("department_profiles")
            @Expose
            var department_profiles: List<User.UserProfile.DepartmentProfiles>,
            @SerializedName("warehouse_ids")
            @Expose
            var warehouse_ids: List<String>
        )
    }
}