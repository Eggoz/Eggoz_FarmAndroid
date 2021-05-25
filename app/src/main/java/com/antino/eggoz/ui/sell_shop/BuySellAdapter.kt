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
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.ui.sell_shop.model.ItemList
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class BuySellAdapter(
    private var mcontex:MainActivity,
    private var result: List<ItemList.ItemResult>?,
    private var limit: Boolean,
    private var from:String
) : RecyclerView.Adapter<BuySellAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var listItem: View


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem =
            layoutInflater.inflate(R.layout.item_buy_sell, parent, false)
        context = listItem.context
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        if (limit) {
            if (result?.size!! > 10)
                return 10
            else
                return result?.size!!
        } else
            return result?.size!!
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtitemname.text = result!![position].name

        /*if (result!![position].name.length > 14) {
            holder.txtitemname.text = result!![position].name.substring(0, 10) + "...."
        } else {*/
            holder.txtitemname.text = result!![position].name
//        }

        holder.txtitemprice.text = "Rs.${result!![position].currentPrice}"
        if (!result!![position].feedProductDivision.isVisible)
            holder.cardViewMain.visibility = View.GONE
        Glide.with(context)
            .asBitmap()
            .load(result!![position].feed_product_image)
            .centerInside()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logo1)
            .into(holder.imgitem)
        listItem.setOnClickListener {
            mcontex.loadDetailFrag(result!![position].id,from)

        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtitemname: TextView = itemView.findViewById(R.id.txt_item_categories_item_name)
        val txtitemprice: TextView = itemView.findViewById(R.id.txt_item_buy_sell_item_price)
        val imgitem: ImageView = itemView.findViewById(R.id.img_buy_sell_item)
        val cardViewMain: CardView = itemView.findViewById(R.id.cardViewMain)


    }

}