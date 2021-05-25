package com.antino.eggoz.ui.item

interface ItemDetailCallback {
    fun loadDetailFrag(id:Int,from:String)
    fun loadDetailFragback(from:String)
}