package com.antino.eggoz.ui.sell_shop

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.R
import com.antino.eggoz.ui.sell_shop.model.CatList
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class CategoriesAdapter(var callback:SellShopFragment,
    var results: List<CatList.Result>?=null
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var listItem: View
    private var size: Int=0



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem=
            layoutInflater.inflate(R.layout.item_categories, parent, false)
        context = listItem.context
        return ViewHolder(listItem)
    }



    override fun getItemCount(): Int {
        return results?.size!!
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!results!![position].isVisible)
            holder.cat_main.visibility=View.GONE

        holder.txtitemname.text = results!![position].name

        Glide.with(context)
            .asBitmap()
            .load(results!![position].image)
            .centerInside()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logo1)
            .into(holder.imgitem)
       /* Picasso.get().load(img[position]).networkPolicy(NetworkPolicy.NO_CACHE)
            .error(R.drawable.logo1).into(holder.imgitem)*/
        listItem.setOnClickListener {
            callback.callback(results!![position].id)

        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtitemname: TextView = itemView.findViewById(R.id.txt_item_categories_item_name)
        val imgitem: ImageView = itemView.findViewById(R.id.img_buy_sell_item)
        val cat_main: CardView = itemView.findViewById(R.id.cat_main)


    }

}