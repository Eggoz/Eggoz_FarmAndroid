package com.antino.eggoz.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class AlertFarmAdapter(
    private var img: ArrayList<String>,
    private var title: ArrayList<String>
)  : RecyclerView.Adapter<AlertFarmAdapter.ViewHolder>( ) {
    private lateinit var context: Context


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.item_home_alert_farm_list, parent, false)
        context=listItem.context
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return img.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txttitle.text= title[position]

        Glide.with(context)
            .asBitmap()
            .load(img[position])
            .centerInside()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logo1)
            .into(holder.imgtop)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txttitle: TextView = itemView.findViewById(R.id.item_title)

        val imgtop: ImageView = itemView.findViewById(R.id.banner_image)


    }

}
