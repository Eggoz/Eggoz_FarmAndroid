package com.antino.eggoz.ui.profile.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FlockBreed(

    @SerializedName("count")
    @Expose
    var count: Int,

    @SerializedName("next")
    @Expose
    var next: Int,

    @SerializedName("previous")
    @Expose
    var previous: Int,

    @SerializedName("results")
    @Expose
    var breed: List<Flock_Breed>) {
    class Flock_Breed(
        @SerializedName("id")
        @Expose
        var id: Int,
        @SerializedName("breed_name")
        @Expose
        var breed_name: String)
}