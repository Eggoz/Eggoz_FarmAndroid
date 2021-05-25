package com.antino.eggoz.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomCart::class],version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun deatailcart() : CartDao
}