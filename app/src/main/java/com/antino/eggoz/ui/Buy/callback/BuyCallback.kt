package com.antino.eggoz.ui.Buy.callback

interface BuyCallback {
    fun loadAddAddress(
        from: String,
        id: Int,
        name: String,
        img: String,
        price: String,
        qnt: Int,
        des: String,
        tax: Boolean)

}