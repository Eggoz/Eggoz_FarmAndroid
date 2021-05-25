package com.antino.eggoz.ui.home

interface HomeCallback {
    fun HomeCallbackdailyInputMissed(){}
    fun HomeCallbackexoplayer(link:String,id:Int){}
    fun loadSchedule(){}
}