package com.antino.eggoz.ui.profile.Model

import com.antino.eggoz.view.data.LoginUser
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Farm(
    @SerializedName("errors")
    @Expose
    var errors: List<LoginUser.Error>?,
    @SerializedName("error_type")
    @Expose
    var errorType: String,
    @SerializedName("count")
    @Expose
    var count: Int?=null,
    @SerializedName("next")
    @Expose
    var next: Any,
    @SerializedName("previous")
    @Expose
    var previous: Any,
    @SerializedName("results")
    @Expose
    var results: List<Result>? = null
) {
    class Result(

        @SerializedName("id")
        @Expose
        var id: Int,
        @SerializedName("sheds")
        @Expose
        var sheds: List<Shed>? = null,
        @SerializedName("farmer")
        @Expose
        var farmer: Farmer,
        @SerializedName("farm_name")
        @Expose
        var farmName: String,
        @SerializedName("billing_farm_address_same")
        @Expose
        var billingFarmAddressSame: Boolean,
        @SerializedName("created_at")
        @Expose
        var createdAt: String,
        @SerializedName("updated_at")
        @Expose
        var updatedAt: String,
        @SerializedName("number_of_layer_shed")
        @Expose
        var numberOfLayerShed: Int,
        @SerializedName("number_of_grower_shed")
        @Expose
        var numberOfGrowerShed: Int,
        @SerializedName("number_of_broiler_shed")
        @Expose
        var number_of_broiler_shed: Int,
        @SerializedName("farm_type")
        @Expose
        var farmType: String,
        @SerializedName("farm_layer_type")
        @Expose
        var farmLayerType: String,
        @SerializedName("is_feed_mixed")
        @Expose
        var isFeedMixed: Boolean,
        @SerializedName("feed_mix_photo_url")
        @Expose
        var feedMixPhotoUrl: String,
        @SerializedName("feed_mix_remarks")
        @Expose
        var feedMixRemarks: String,
        @SerializedName("is_fssai_license_present")
        @Expose
        var isFssaiLicensePresent: Boolean,
        @SerializedName("fssai_license_photo_url")
        @Expose
        var fssaiLicensePhotoUrl: String,
        @SerializedName("fssai_license_no")
        @Expose
        var fssaiLicenseNo: String,
        @SerializedName("is_fssai_verified")
        @Expose
        var isFssaiVerified: Boolean,
        @SerializedName("is_vehicle_available")
        @Expose
        var isVehicleAvailable: Boolean,
        @SerializedName("vehicle_photo_url")
        @Expose
        var vehiclePhotoUrl: String,
        @SerializedName("vehicle_no")
        @Expose
        var vehicleNo: Any,
        @SerializedName("GSTIN")
        @Expose
        var gstin: String,
        @SerializedName("PAN_CARD")
        @Expose
        var panCard: String,
        @SerializedName("is_gst_verified")
        @Expose
        var isGstVerified: Boolean,
        @SerializedName("is_pan_verified")
        @Expose
        var isPanVerified: Boolean,
        @SerializedName("is_complete")
        @Expose
        var isComplete: Boolean,
        @SerializedName("necc_zone")
        @Expose
        var neccZone: String,
        @SerializedName("shipping_address")
        @Expose
        var shippingAddress: ShippingAddress,
        @SerializedName("billing_address")
        @Expose
        var billingAddress: BillingAddress,
        @SerializedName("supplyPerson")
        @Expose
        var supplyPerson: String
    ) {
        class ShippingAddress(

            @SerializedName("id")
            @Expose
            var id:Int,
        @SerializedName("default_address_user")
        @Expose
        var defaultAddressUser: List<Any>? = null,
        @SerializedName("user_addresses_user")
        @Expose
        var userAddressesUser:List<Any> ?= null,
        @SerializedName("address_name")
        @Expose
        var  addressName:String,
        @SerializedName("building_address")
        @Expose
        var buildingAddress:String,
        @SerializedName("street_address")
        @Expose
        var streetAddress:String,
        @SerializedName("city")
        @Expose
        var city:Any,
        @SerializedName("landmark")
        @Expose
        var landmark:String,
        @SerializedName("pinCode")
        @Expose
        var pinCode:Int,
        @SerializedName("latitude")
        @Expose
        var latitude:Any,
        @SerializedName("longitude")
        @Expose
        var longitude:Any,
        @SerializedName("date_added")
        @Expose
        var  dateAdded:String,
        @SerializedName("billing_city")
        @Expose
        var billingCity:String
        )
        class BillingAddress(
            @SerializedName("id")
            @Expose
            var id:Int,
        @SerializedName("default_address_user")
        @Expose
        var defaultAddressUser :List<Object>?= null,
        @SerializedName("user_addresses_user")
        @Expose
        var userAddressesUser:List<Object>? = null,
        @SerializedName("address_name")
        @Expose
        var addressName:String,
        @SerializedName("building_address")
        @Expose
        var buildingAddress:String,
        @SerializedName("street_address")
        @Expose
        var streetAddress:String,
        @SerializedName("city")
        @Expose
        var city:Any,
        @SerializedName("landmark")
        @Expose
        var landmark:String,
        @SerializedName("pinCode")
        @Expose
        var pinCode:Int,
        @SerializedName("latitude")
        @Expose
        var latitude:Any,
        @SerializedName("longitude")
        @Expose
        var longitude:Any,
        @SerializedName("date_added")
        @Expose
        var  dateAdded:String,
        @SerializedName("billing_city")
        @Expose
        var billingCity:String
        )
        class Farmer(@SerializedName("id")
                     @Expose
                     var id:Int,
        @SerializedName("farmer")
        @Expose
        var farmer:Farmer__1,
        @SerializedName("necc_zone")
        @Expose
        var neccZone:String){
            class Farmer__1(
                @SerializedName("id")
                @Expose
                var id:Int,
            @SerializedName("name")
            @Expose
            var name:String,
            @SerializedName("email")
            @Expose
            var email:String,
            @SerializedName("phone_no")
            @Expose
            var phoneNo:String,
            @SerializedName("default_address")
            @Expose
            var defaultAddress:Any,
            @SerializedName("addresses")
            @Expose
            var addresses: List<Any>? = null
            )
        }
        class Shed(

            @SerializedName("id")
            @Expose
            var id: Int,
            @SerializedName("flocks")
            @Expose
            var flocks: List<Flock1>? = null,
            @SerializedName("shed_type")
            @Expose
            var shedType: String,
            @SerializedName("shed_name")
            @Expose
            var shedName: String,
            @SerializedName("total_active_bird_capacity")
            @Expose
            var totalActiveBirdCapacity: Int,
            @SerializedName("farm")
            @Expose
            var farm: Int
        ) {
            class Flock1(
                @SerializedName("id")
                @Expose
                var id: Int,
                @SerializedName("breed")
                @Expose
                var breed: Breed,
                @SerializedName("daily_inputs")
                @Expose
                var dailyInputs: List<DailyInput>? = null,
                @SerializedName("flock_name")
                @Expose
                var flockName: String,
                @SerializedName("flock_id")
                @Expose
                var flockId: String,
                @SerializedName("age")
                @Expose
                var age: Int,
                @SerializedName("initial_capacity")
                @Expose
                var initialCapacity: Int,
                @SerializedName("current_capacity")
                @Expose
                var currentCapacity: Int,
                @SerializedName("last_daily_input_date")
                @Expose
                var lastDailyInputDate: String ?=null,
                @SerializedName("egg_type")
                @Expose
                var eggType: String,
                @SerializedName("initial_production")
                @Expose
                var initialProduction: Int,
                @SerializedName("total_production")
                @Expose
                var totalProduction: Int,
                @SerializedName("shed")
                @Expose
                var shed: Int
            ){
                class DailyInput(


                    @SerializedName("flock")
                    @Expose
                    var flock:Int,
                @SerializedName("date")
                @Expose
                var  date:String,
                @SerializedName("egg_daily_production")
                @Expose
                var eggDailyProduction:Int,
                @SerializedName("mortality")
                @Expose
                var mortality:Int,
                @SerializedName("feed")
                @Expose
                var feed:String,
                @SerializedName("weight")
                @Expose
                var weight:String,
                @SerializedName("culls")
                @Expose
                var culls:Int,
                @SerializedName("total_active_birds")
                @Expose
                var totalActiveBirds:Int,
                @SerializedName("broken_egg_in_production")
                @Expose
                var brokenEggInProduction:Int,
                @SerializedName("broken_egg_in_operation")
                @Expose
                var brokenEggInOperation:Int,
                @SerializedName("remarks")
                @Expose
                var remarks:String
                )
                class Breed(
                    @SerializedName("id")
                    @Expose
                    var id:Int,
                @SerializedName("breed_name")
                @Expose
                var breedName:String)

            }
        }
    }

}