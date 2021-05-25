package com.antino.eggoz.view.otherfragment

interface CartCallback {
    fun loadBuyFragment(from:String,id:Int,name:String,img:String,price:String,qnt:Int,des:String,tax:Boolean)
}