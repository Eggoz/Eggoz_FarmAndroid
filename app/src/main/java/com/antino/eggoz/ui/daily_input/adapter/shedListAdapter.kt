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
import java.util.ArrayList

class shedListAdapter(
    private val contextmain: MainActivity,
    private val results: List<Farm.Result.Shed>?=null
) : RecyclerView.Adapter<shedListAdapter.ViewHolder>() {
    private lateinit var listItem: View

    private lateinit var layer: ArrayList<Farm.Result.Shed>
    private lateinit var grawer: ArrayList<Farm.Result.Shed>

    lateinit var adapter:FlockListAdapter

    var ldate=""


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem=
            layoutInflater.inflate(R.layout.item_dailyinputshed_list, parent, false)
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return results?.size!!
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.recycle_view_shed.layoutManager = LinearLayoutManager(
            contextmain,
            LinearLayoutManager.VERTICAL,
            false
        )
        holder.recycle_view_shed.setHasFixedSize(true)
        if (results!![position].shedType == "Layer")
         adapter= FlockListAdapter(
            contextmain ,
            results[position].flocks,true
        )
        else adapter= FlockListAdapter(
            contextmain ,
            results[position].flocks,false
        )
        holder.recycle_view_shed.adapter = adapter
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /*   val txt_farmName: TextView = itemView.findViewById(R.id.txt_farm_name)
           val txt_shadeName: TextView = itemView.findViewById(R.id.txt_shade_name)
           val txt_flockName: TextView = itemView.findViewById(R.id.txt_flock_name)
           val txt_lastUpdateDate: TextView = itemView.findViewById(R.id.txt_last_update)
           val txt_BreedName: TextView = itemView.findViewById(R.id.txt_BreedName)


           val btn_addInput: Button = itemView.findViewById(R.id.btn_addInput)
   */

        val recycle_view_shed: RecyclerView = itemView.findViewById(R.id.recycle_view_shed)





    }

}