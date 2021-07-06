package com.antino.eggoz.ui.daily_input.model

import androidx.annotation.Keep
import com.antino.eggoz.view.data.LoginUser
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class AddDailyInput(
    @SerializedName("flock")
    @Expose
    var flock: Int,
    @SerializedName("date")
    @Expose
    var date: String,
    @SerializedName("egg_daily_production")
    @Expose
    var eggDailyProduction: Int,
    @SerializedName("mortality")
    @Expose
    var mortality: Int,
    @SerializedName("feed")
    @Expose
    var feed: String,
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
    var remarks: String,
    @SerializedName("errors")
    @Expose
    var errors: List<LoginUser.Error>?,
    @SerializedName("error_type")
    @Expose
    var errorType: String
    ) {
}