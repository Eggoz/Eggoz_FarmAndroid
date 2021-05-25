package com.antino.eggoz.view.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CityList(@SerializedName("count")
               @Expose
               var count: String,
               @SerializedName("next")
               @Expose
               var next: String,
               @SerializedName("previous")
               @Expose
               var previous: String,
               @SerializedName("results")
               @Expose
               var results:List<Result>) {
    class Result(
        @SerializedName("id")
        @Expose
        var  id:Int,
        @SerializedName("city_name")
        @Expose
        var  city_name:String,
        @SerializedName("state")
        @Expose
        var  state:String,
        @SerializedName("country")
        @Expose
        var  country:String
    )

}