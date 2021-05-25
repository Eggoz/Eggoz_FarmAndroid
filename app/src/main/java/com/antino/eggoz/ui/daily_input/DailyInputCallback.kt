package com.antino.eggoz.ui.daily_input

interface DailyInputCallback {
    fun dailyInputCallback(flockid:Int,flocktotalactivebird: Int,lastUpdate:String,type:String){}
}