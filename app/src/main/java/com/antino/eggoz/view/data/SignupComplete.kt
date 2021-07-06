package com.antino.eggoz.view.data

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class SignupComplete(
    @SerializedName("errors")
    @Expose
    var errors: List<Error>,
    @SerializedName("error_type")
    @Expose
    var error_type: String,
    var status: Boolean= false
) {
    class Error(
        @SerializedName("message")
        @Expose
        var message: String,
        @SerializedName("field")
        @Expose
        var field: String


    )
}