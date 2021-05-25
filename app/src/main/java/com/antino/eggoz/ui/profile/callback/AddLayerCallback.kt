package com.antino.eggoz.ui.profile.callback

interface AddLayerCallback {
    fun addflock(flockname: String,breed:String,flockage:String,flockqunt:String,eggtype:String,flockid:Int){}
    fun updateflock(position: Int,flockname: String,breed:String,flockage:String,flockqunt:String,eggtype:String,flockid:Int){}
    fun updateposion(position: Int){}
    fun deleteposion(position: Int){}
    fun back(){}
}