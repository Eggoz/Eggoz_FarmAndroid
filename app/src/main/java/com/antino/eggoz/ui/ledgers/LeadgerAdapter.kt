package com.antino.eggoz.ui.ledgers

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.util.*

class LeadgerAdapter(
    private val img: ArrayList<String>,
    private val item_headings: ArrayList<String>,
    private val item_des: ArrayList<String>,
    private val item_price: ArrayList<String>
) : RecyclerView.Adapter<LeadgerAdapter.ViewHolder>() {


    private lateinit var context: Context
    private lateinit var listItem: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem=
            layoutInflater.inflate(R.layout.item_leadger_list, parent, false)
        context = listItem.context
        return ViewHolder(listItem)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.heading.text=item_headings[position]
        holder.description.text=item_des[position]
        holder.remark.text=item_headings[position]
        val bal:Double=item_price[position].toDouble()
        if (bal<0)
            holder.price.setTextColor( ContextCompat.getColor(context,R.color.red))
        else holder.price.setTextColor(ContextCompat.getColor(context,R.color.green))
        holder.price.text="INR ${ item_price[position] }"
        Glide.with(context)
            .asBitmap()
            .load(img[position])
            .centerInside()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logo1)
            .into(holder.pic)

        listItem.setOnClickListener {
            if (holder.remark.isVisible) {
                holder.remark.visibility = View.GONE
                holder.balance.visibility=View.GONE
            }
            else {
                holder.remark.visibility = View.VISIBLE
                holder.balance.visibility=View.VISIBLE
            }
        }


    }

    override fun getItemCount(): Int {
        return img.size
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val heading:TextView = itemView.findViewById(R.id.txt_leadger_heading)
        val description:TextView = itemView.findViewById(R.id.txt_leadger_des)
        val price:TextView = itemView.findViewById(R.id.txt_leadger_price)
        val pic:ImageView = itemView.findViewById(R.id.img_leadger)
        val remark:TextView = itemView.findViewById(R.id.txt_remark_leadger)
        val balance:TextView = itemView.findViewById(R.id.txt_balance)

    }
}