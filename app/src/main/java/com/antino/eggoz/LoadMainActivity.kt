package com.antino.eggoz

interface LoadMainActivity {
    fun loadHome()
    fun loadDailyInput()
    fun loadSellShop()
    fun loadFeed()
    fun loadConsulting()
    fun loadTrackOrder()
    fun loadProfile()
    fun loadCart()
    fun loadAddLayer(id:Int,from:String)
    fun loadSummary(flock_id:Int)
    fun AddFlock(id:Int,type:String)
    fun loadEditProfile()
    fun scheduleDetail(id: Int)

    fun loadComment(mid: Int,comment:Int)
    fun loadWebview(url: String)
}