package com.antino.eggoz.ui.daily_input.model

import androidx.annotation.Keep
import com.antino.eggoz.view.data.LoginUser
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class DailInput(
    @SerializedName("count")
    @Expose
    val count: Int,
    @SerializedName("next")
    @Expose
    val next: Any,
    @SerializedName("previous")
    @Expose
    val previous: Any,
    @SerializedName("results")
    @Expose
    val results: List<Result>? = null,
    @SerializedName("errors")
    @Expose
    var errors: List<LoginUser.Error>?,
    @SerializedName("error_type")
    @Expose
    var errorType: String
) {
    class Result(
        @SerializedName("id")
        @Expose
        val id: Int,
        @SerializedName("flock")
        @Expose
        val flock: Int,
        @SerializedName("date")
        @Expose
        val date: String,
        @SerializedName("egg_daily_production")
        @Expose
        val eggDailyProduction: Int,
        @SerializedName("mortality")
        @Expose
        val mortality: Int,
        @SerializedName("feed")
        @Expose
        val feed: String,
        @SerializedName("weight")
        @Expose
        var weight: String,
        @SerializedName("culls")
        @Expose
        var culls: Int,
        @SerializedName("total_active_birds")
        @Expose
        var totalActiveBirds: Int,
        @SerializedName("broken_egg_in_production")
        @Expose
        var brokenEggInProduction: Int,
        @SerializedName("broken_egg_in_operation")
        @Expose
        var brokenEggInOperation: Int,
        @SerializedName("remarks")
        @Expose
        var remarks: String
    )
}