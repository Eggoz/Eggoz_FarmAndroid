package com.antino.eggoz.view.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SignupPassword(
    @SerializedName("error_type")
    @Expose
    var errorType: String,

    @SerializedName("errors")
    @Expose
    var errors: List<Error>,
    @SerializedName("id")
    @Expose
    var id: String,
    @SerializedName("email")
    @Expose
    var email: String,
    @SerializedName("username")
    @Expose
    var username: String,
    @SerializedName("phone_no")
    @Expose
    var phone_no: String,
    @SerializedName("token")
    @Expose
    var token: String,
    @SerializedName("date_joined")
    @Expose
    var date_joined: String

) {
    class Error(
        @SerializedName("message")
        @Expose
        var  message:String,
        @SerializedName("field")
        @Expose
        var  field:String

    )
}