package com.antino.eggoz.view.data

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class SignUpUser(
    @SerializedName("Result")
    @Expose
    var otpmessage: String,
    @SerializedName("otp")
    @Expose
    var otp: String,
    @SerializedName("error_type")
    @Expose
    var errorType: String,

    @SerializedName("errors")
    @Expose
    var errors: List<Error>,
    @SerializedName("error")
    @Expose
    var error: String,
    @SerializedName("result")
    @Expose
    var result: String,
    @SerializedName("success")
    @Expose
    var success: String) {
    class Error(
        @SerializedName("message")
        @Expose
        var  message:String,
        @SerializedName("field")
        @Expose
        var  field:String

    )
}