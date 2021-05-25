package com.antino.eggoz.ui.profile.callback

import android.location.Address

interface ProfileCallback {
    fun addFarm(){}
    fun Farmdata(farmName:String,buildingno: String,landmark: String,city: String,state: String,
                 pincode: String,NoOfBroiler:Int,number_of_layer_shed:Int,number_of_grower_shed:Int,farm_layer_type:String){}

    fun addShades(ShadeName:String,totalbirdcapacity: String,WhiteEggsdailyprod:String,WhiteEggsdailycapacity:String
        ,BrownEggsdailyprod:String,BrownEggsbirdcapacity:String,FormerType:String
                  ,FolkType:String,noOfShads:String,noOfGrower:String){}

    fun addGrower(GrowerName:String,totalbirdcapacity: String,age:String,Vaccination:String,noOfShads:String,noOfGrower:String){}
}