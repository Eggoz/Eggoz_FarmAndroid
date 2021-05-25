package com.antino.eggoz.ui.profile.Model

class Farmdata() {
    lateinit var farmName:String
    lateinit var farmAddress: String
    lateinit var shadesSize: String
    lateinit var growerSize: String
    var shades:ArrayList<Shades>?=null
    var grower: ArrayList<Grower>?=null

}