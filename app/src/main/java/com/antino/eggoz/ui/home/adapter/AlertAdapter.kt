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
import com.antino.eggoz.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class AlertAdapter(
    private var img: ArrayList<String>,
    private var title: ArrayList<String>,
    private var whiteeggs: ArrayList<String>,
    private var browneggs: ArrayList<String>,
    private var timestamp: ArrayList<String>
)  : RecyclerView.Adapter<AlertAdapter.ViewHolder>( ) {
    private lateinit var context: Context


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.item_home_alert_list, parent, false)
        context=listItem.context
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return img.size+1
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("data","img size ${position} ${img.size}")




        if (position<=img.size-1){
            holder.addItem.visibility=View.GONE
            holder.viewItem.visibility=View.VISIBLE

            holder.txttitle.text= title[position]
            holder.txtwhiteeggs.text= whiteeggs[position]
            holder.txtbrowneggs.text= browneggs[position]
            holder.txttimestamp.text= timestamp[position]

            Glide.with(context)
                .asBitmap()
                .load(img[position])
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.logo1)
                .into(holder.imgtop)
        }else{
            holder.addItem.visibility=View.VISIBLE
            holder.viewItem.visibility=View.GONE

        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txttitle: TextView = itemView.findViewById(R.id.item_title)
        val txtwhiteeggs: TextView = itemView.findViewById(R.id.item_title_white_egg)
        val txtbrowneggs: TextView = itemView.findViewById(R.id.item_title_brown_egg)
        val txttimestamp: TextView = itemView.findViewById(R.id.item_title_date)

        val imgtop: ImageView = itemView.findViewById(R.id.banner_image)

        val addItem: ConstraintLayout=itemView.findViewById(R.id.item_add)
        val viewItem: ConstraintLayout=itemView.findViewById(R.id.item)



    }

}
