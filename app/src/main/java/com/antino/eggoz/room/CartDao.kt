package com.antino.eggoz.room

import androidx.room.*

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg roomCart: RoomCart)

    @Delete
    suspend fun delete(roomCart: RoomCart)

    @Query("SELECT * FROM cart")
    fun getAll(): List<RoomCart>

    @Update
    suspend fun updateCart(vararg roomCart: RoomCart)


    @Query("update cart set quantaty=:qnt, price= :price where id= :mid")
    fun updateCart(mid:Int,qnt:Int,price:String)

    @Query("delete from cart where id= :mid")
    fun deletebyid(mid:Int)

    @Query("Select * from cart")
    fun getCart():List<RoomCart>

    @Query("delete from cart")
    suspend fun deletecart()
}