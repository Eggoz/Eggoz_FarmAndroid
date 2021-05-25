package com.antino.eggoz.ui.order.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.R
import com.antino.eggoz.ui.order.OrderSummaryFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.util.*

class OrderSummaryAdapter(val mcontext: OrderSummaryFragment,
    private val img: ArrayList<String>,
    private val titile: ArrayList<String>,
    private val des: ArrayList<String>,
    private val time: ArrayList<String>
) : RecyclerView.Adapter<OrderSummaryAdapter.ViewHolder>() {


    private lateinit var context: Context
    private lateinit var listItem: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem =
            layoutInflater.inflate(R.layout.item_ordersummary_list, parent, false)
        context = listItem.context
        return ViewHolder(listItem)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txt_title.text=titile[position]
        holder.txt_price.text=des[position]
        holder.txt_qnt.text=time[position]

        Glide.with(context)
            .asBitmap()
            .load(img[position])
            .centerInside()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logo1)
            .into(holder.img_pic)

        holder.adds_Layout.visibility=View.GONE


        listItem.setOnClickListener { if (holder.adds_Layout.isVisible)
            holder.adds_Layout.visibility=View.GONE
        else holder.adds_Layout.visibility=View.VISIBLE}

        holder.txt_track.setOnClickListener { mcontext.ordertrack() }


    }

    override fun getItemCount(): Int {
        return img.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_title: TextView = itemView.findViewById(R.id.txt_order_summary_name)
        val txt_price: TextView = itemView.findViewById(R.id.txt_order_summary_price)
        val txt_qnt: TextView = itemView.findViewById(R.id.txt_order_summary_qnt)
        val img_pic: ImageView = itemView.findViewById(R.id.img_order_summary)
        val txt_track:TextView = itemView.findViewById(R.id.txt_order_summary_Track_Order)

        val adds_Layout:ConstraintLayout=itemView.findViewById(R.id.layout_address_payment)


    }


}