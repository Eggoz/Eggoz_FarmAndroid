package com.antino.eggoz.ui.daily_input

interface DailyInputDetailCallback {
    fun medCallback(medid:Int,pos: Int,qunt:String){}
    fun medupdateCallback(medid:Int,pos: Int,qunt:String){}
    fun meddeleteCallback(pos: Int){}
    fun mededitCallback(pos: Int,qnt: Int,id:Int){}
}