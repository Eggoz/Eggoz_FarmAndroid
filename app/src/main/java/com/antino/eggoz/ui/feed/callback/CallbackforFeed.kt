package com.antino.eggoz.ui.feed.callback

interface CallbackforFeed {
    fun likedislike(pos:Int){}
    fun postlikedislike(pos: Int){}
    fun getcomment(id:Int,likecount:Int){}
}