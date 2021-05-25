package com.antino.eggoz.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.R
import com.antino.eggoz.ui.home.model.NeccRate
import kotlin.math.roundToInt

class NeccAdapter(
    var results: List<NeccRate.Result>? = null
) : RecyclerView.Adapter<NeccAdapter.ViewHolder>() {


    private lateinit var context: Context
    private lateinit var listItem: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem =
            layoutInflater.inflate(R.layout.item_necc_list, parent, false)
        context = listItem.context
        return ViewHolder(listItem)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txttitle.text = results!![position].neccCity.name
        holder.txtdes.text ="Rs. ${String.format("%.2f",results!![position].currentRate.toDouble().roundToInt()/100.0)}"


    }

    override fun getItemCount(): Int {
        return results?.size!!
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txttitle: TextView = itemView.findViewById(R.id.txt_title)
        val txtdes: TextView = itemView.findViewById(R.id.txt_des)


    }


}