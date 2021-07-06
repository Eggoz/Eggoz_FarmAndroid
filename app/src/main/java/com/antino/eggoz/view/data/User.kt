package com.antino.eggoz.view.data

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class User(
    @SerializedName("success")
    @Expose
    var success: String,

    @SerializedName("id")
    @Expose
    var id: String,
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
    var default_address: String,
    @SerializedName("addresses")
    @Expose
    var addresses: ArrayList<String>,
    @SerializedName("userProfile")
    @Expose
    var userProfile: UserProfile,
    @SerializedName("userData")
    @Expose
    var userData: String,
    @SerializedName("userCities")
    @Expose
    var userCities: ArrayList<String>,
    @SerializedName("date_joined")
    @Expose
    var date_joined: String,
    @SerializedName("errors")
@Expose
var errors: List<LoginUser.Error>?,
    @SerializedName("error_type")
    @Expose
    var errorType: String
) {
    class UserProfile(
        @SerializedName("id")
        @Expose
        var id: String,

        @SerializedName("departments")
        @Expose
        var departments: ArrayList<String>,
        @SerializedName("department_profiles")
        @Expose
        var department_profiles: ArrayList<DepartmentProfiles>,
        @SerializedName("warehouse_ids")
        @Expose
        var warehouse_ids: List<String>
    ){

        class DepartmentProfiles(
            @SerializedName("farmerProfile")
            @Expose
            var farmerProfile: Int,
            @SerializedName("management_status")
            @Expose
            var management_status: String)
    }
}