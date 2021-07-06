package com.antino.eggoz.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R

class IotListAdapter(private val contextmain: MainActivity
) : RecyclerView.Adapter<IotListAdapter.ViewHolder>() {
    private lateinit var listItem: View


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem=
            layoutInflater.inflate(R.layout.item_iot_list, parent, false)
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return 3
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txt_temp: TextView = itemView.findViewById(R.id.txt_temp)
        val txt_Humidity: TextView = itemView.findViewById(R.id.txt_Humidity)
        val txt_Ammonia: TextView = itemView.findViewById(R.id.txt_Ammonia)
        val txt_date: TextView = itemView.findViewById(R.id.txt_date)

    }

}