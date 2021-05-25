package com.antino.eggoz.view.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ErrorResponse(
    @SerializedName("error_type")
    @Expose
    var errorType: String,

    @SerializedName("errors")
    @Expose
    var errors: List<Error>) {

    class Error(
        @SerializedName("message")
        @Expose
        var  message:String,
        @SerializedName("field")
        @Expose
        var  field:String

    )
}