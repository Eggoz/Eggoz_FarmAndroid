package com.antino.eggoz.ui.notification

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
import java.util.*

class NotificationAdapter(
    private val img: ArrayList<String>,
    private val titile: ArrayList<String>,
    private val des: ArrayList<String>,
    private val time: ArrayList<String>
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {


    private lateinit var context: Context
    private lateinit var listItem: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem =
            layoutInflater.inflate(R.layout.item_notification_list, parent, false)
        context = listItem.context
        return ViewHolder(listItem)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txt_title.text=titile[position]
        holder.txt_des.text=des[position]
        holder.txt_notification_time.text=time[position]

        Glide.with(context)
            .asBitmap()
            .load(img[position])
            .centerInside()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logo1)
            .into(holder.img_pic)

    }

    override fun getItemCount(): Int {
        return img.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_title: TextView = itemView.findViewById(R.id.txt_notification_name)
        val txt_des: TextView = itemView.findViewById(R.id.txt_noticication_des)
        val txt_notification_time: TextView = itemView.findViewById(R.id.txt_notification_time)
        val img_pic: ImageView = itemView.findViewById(R.id.img_noticication)


    }


}