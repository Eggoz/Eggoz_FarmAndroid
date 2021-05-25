package com.antino.eggoz.ui.schedule

interface AdditemDialog {
    fun onclick(pos:String,qnt:String,rate:String,totalamt:String,remark:String){}
    fun updateclick(position:String,pos:Int,qnt:String,rate:String,totalamt:String,remark:String){}
    fun custom_dialog(pos:Int){}
    fun delete(pos:Int){}


}