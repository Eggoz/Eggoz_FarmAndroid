package com.antino.eggoz.ui.profile.callback

interface locationCallback {
    fun location(building_name: String,landmark:String,city:String,state:String,pincode:String){}
}