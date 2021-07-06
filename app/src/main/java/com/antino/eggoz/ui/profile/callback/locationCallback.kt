package com.antino.eggoz.ui.profile.callback

interface locationCallback {
    fun location(building_name: String?=null,landmark:String?=null,city:String?=null,state:String?=null,pincode:String?=null){}
}