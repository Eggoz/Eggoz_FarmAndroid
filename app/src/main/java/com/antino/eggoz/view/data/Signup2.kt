package com.antino.eggoz.view.data

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Keep
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
        var userData: UserData2,
        @SerializedName("userCities")
        @Expose
        var userCities: List<UserCity>,
        @SerializedName("date_joined")
        @Expose
        var date_joined: String
    ) {
        class UserData2(


            @SerializedName("id")
            @Expose
             val id: Int? = null,

            @SerializedName("employee_id")
        @Expose
         val employeeId: String? = null,

        @SerializedName("profile_photo_url")
        @Expose
         val profilePhotoUrl: Any? = null,

        @SerializedName("created_at")
        @Expose
         val createdAt: String? = null,

        @SerializedName("updated_at")
        @Expose
         val updatedAt: String? = null,

        @SerializedName("online")
        @Expose
         val online: Boolean? = null,

        @SerializedName("latitude")
        @Expose
         val latitude: Any? = null,

        @SerializedName("longitude")
        @Expose
         val longitude: Any? = null,

        @SerializedName("location_updated_at")
        @Expose
         val locationUpdatedAt: Any? = null,

        @SerializedName("is_profile_complete")
        @Expose
         val isProfileComplete: Boolean? = null,

        @SerializedName("is_profile_verified")
        @Expose
         val isProfileVerified: Boolean? = null,

        @SerializedName("experience")
        @Expose
         val experience: Any? = null,

        @SerializedName("rating")
        @Expose
         val rating: String? = null,

        @SerializedName("aadhar_photo_url")
        @Expose
         val aadharPhotoUrl: Any? = null,

        @SerializedName("aadhar_no")
        @Expose
         val aadharNo: Any? = null,

        @SerializedName("aadhar_status")
        @Expose
         val aadharStatus: String? = null,

        @SerializedName("pancard_photo_url")
        @Expose
         val pancardPhotoUrl: Any? = null,

        @SerializedName("pancard_no")
        @Expose
         val pancardNo: Any? = null,

        @SerializedName("pancard_status")
        @Expose
         val pancardStatus: String? = null,

        @SerializedName("userProfile")
        @Expose
         val userProfile: Int? = null
        )

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
            var warehouse_ids: List<WarehouseId>
        ) {
            class WarehouseId(

                @SerializedName("supplyWarehouseId")
                @Expose
                val supplyWarehouseId: Int? = null,

                @SerializedName("salesWarehouseId")
                @Expose
                val salesWarehouseId: Int? = null,

                @SerializedName("opsWarehouseId")
                @Expose
                val opsWarehouseId: Int? = null,

                @SerializedName("warehouseId")
                @Expose
                val warehouseId: Int? = null
            )

        }
    }
}