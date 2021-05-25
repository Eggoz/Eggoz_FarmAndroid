package com.antino.eggoz.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.ui.home.model.PopularProducts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ProductAdapter(var mcontex:MainActivity,
    var results: List<PopularProducts.Result>
)  : RecyclerView.Adapter<ProductAdapter.ViewHolder>( ) {
    private lateinit var context: Context

    private lateinit var listItem: View

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem=
            layoutInflater.inflate(R.layout.item_home_product_list, parent, false)
        context=listItem.context
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        if (results.size<4)
        return results.size+1
        else
            return 4
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (results[position].name.length > 14) {
            holder.txttitle.text = results[position].name.substring(0, 10) + "...."
        } else {
            holder.txttitle.text = results[position].name
        }

        holder.txtdes.text=results[position].description
        holder.txtprice.text="Rs.${results[position].currentPrice}"

        Glide.with(context)
            .asBitmap()
            .load(results[position].feedProductImage)
            .centerInside()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logo1)
            .into(holder.imgtop)
        Log.d("data","size $itemCount")

      /*  listItem.setOnClickListener {
            *//* val intent = Intent(context, ItemDetail::class.java)
             intent.putExtra("id", "${result!![position].id}")
             context.startActivity(intent)*//*
            if (position!=itemCount)
            mcontex.loadDetailFrag(results[position].id,"home")
            else
                mcontex.loadSellShop()
        }*/
        if (results.size>3) {
            if (position == 3) {
                holder.item_addmore.visibility = View.VISIBLE
                holder.item.visibility = View.GONE
                listItem.setOnClickListener {
                    mcontex.loadSellShop()
                }
            } else {
                holder.item_addmore.visibility = View.GONE
                holder.item.visibility = View.VISIBLE
                listItem.setOnClickListener {
                    mcontex.loadDetailFrag(results[position].id,"home")
                }
            }
        }else{
            if (results.size+1==position){
                holder.item_addmore.visibility = View.VISIBLE
                holder.item.visibility = View.GONE
                listItem.setOnClickListener {
                    mcontex.loadSellShop()
                }
            }else{
                holder.item_addmore.visibility = View.GONE
                holder.item.visibility = View.VISIBLE
                listItem.setOnClickListener {
                    mcontex.loadDetailFrag(results[position].id,"home")
                }
            }
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txttitle: TextView = itemView.findViewById(R.id.item_title)
        val txtdes: TextView = itemView.findViewById(R.id.item_title_des)
        val txtprice: TextView = itemView.findViewById(R.id.item_title_price)
        val imgtop: ImageView = itemView.findViewById(R.id.banner_image)


        val item_addmore: ConstraintLayout = itemView.findViewById(R.id.item_addmore)
        val item: ConstraintLayout = itemView.findViewById(R.id.item)



    }

}
