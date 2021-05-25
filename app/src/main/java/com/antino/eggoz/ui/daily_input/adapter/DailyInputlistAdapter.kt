package com.antino.eggoz.ui.daily_input.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.ui.profile.Model.Farm
import java.util.*

class DailyInputlistAdapter(
    private val contextmain: MainActivity,
    private val results: List<Farm.Result>?=null
) : RecyclerView.Adapter<DailyInputlistAdapter.ViewHolder>() {
    private lateinit var listItem: View

    private lateinit var layer: ArrayList<Farm.Result.Shed>
    private lateinit var grawer: ArrayList<Farm.Result.Shed>

    var ldate=""


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem=
            layoutInflater.inflate(R.layout.item_dailyinputfarm_list, parent, false)
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return results?.size!!
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.farmName.text= "Farm:- ${results!![0].farmName}"

        holder.recycle_flock.layoutManager = LinearLayoutManager(
            contextmain,
            LinearLayoutManager.VERTICAL,
            false
        )
        holder.recycle_flock.setHasFixedSize(true)
        val adapter = shedListAdapter(
            contextmain,
            results[position].sheds
        )
        holder.recycle_flock.adapter = adapter
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val farmName: TextView = itemView.findViewById(R.id.farm_Name)
        val recycle_flock: RecyclerView = itemView.findViewById(R.id.recycle_flock)





    }

}