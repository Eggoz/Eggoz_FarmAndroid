package com.antino.eggoz.ui.daily_input.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.ui.profile.Model.Farm
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class FlockListAdapter(
    private val contextmain: MainActivity,
    private val results: List<Farm.Result.Shed.Flock1>? = null,
    private var state: Boolean
) : RecyclerView.Adapter<FlockListAdapter.ViewHolder>() {
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
            layoutInflater.inflate(R.layout.item_dailyinputflock_list, parent, false)
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return results?.size!!
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_flock_name.text= results!![position].flockName
        holder.txt_totoalbird.text= results[position].currentCapacity.toString()

        if (state)
            holder.txt_hdep.text= "0 %"
        else{
            holder.txt_hdep.text= "0 Kg"
            holder.txt_hdep_title.text= "Flock Weight"
        }

        if (results[position].dailyInputs!=null && results[position].dailyInputs?.size!=0) {
            holder.txt_feedIntake.text =
                results[position].dailyInputs!![results[position].dailyInputs?.size!! - 1].feed

            if (results[position].dailyInputs!![results[position].dailyInputs?.size!! - 1].weight == "0.000") {
                val hdep:Double=results[position].dailyInputs!![results[position].dailyInputs?.size!! - 1].eggDailyProduction.toDouble() /
                        results[position].dailyInputs!![results[position].dailyInputs?.size!! - 1].totalActiveBirds.toDouble()
                val df = DecimalFormat("####0.00")
                holder.txt_hdep.text ="${df.format(hdep*100)} %"
            }else {
                holder.txt_hdep_title.text= "Flock Weight"
                holder.txt_hdep.text =
                    "${results[position].dailyInputs!![results[position].dailyInputs?.size!! - 1].weight} Kg"
            }
        }


        if ( results[position].lastDailyInputDate !=null)
        holder.txt_last_update.text= results[position].lastDailyInputDate

        val c = Calendar.getInstance().time

        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())


        if (df.format(c).toString()==results[position].lastDailyInputDate)
            holder.btn_addInput.visibility=View.GONE

        Log.d("data","current date ${df.format(c)}")
        val formattedDate = df.format(c)

        ldate=formattedDate

        if (results[position].lastDailyInputDate==null)
            ldate=df.toString()

        holder.btn_addInput.setOnClickListener { contextmain.dailyInputCallback(
            results[position].id,
            results[position].currentCapacity,
            formattedDate
        ,"") }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txt_hdep: TextView = itemView.findViewById(R.id.txt_hdep)
        val txt_hdep_title: TextView = itemView.findViewById(R.id.txt_hdep_title)

        val txt_feedIntake: TextView = itemView.findViewById(R.id.txt_feedIntake)
        val txt_totoalbird: TextView = itemView.findViewById(R.id.txt_totoalbird)
        val txt_last_update: TextView = itemView.findViewById(R.id.txt_last_update)

        val txt_flock_name: TextView = itemView.findViewById(R.id.txt_flock_name)
        val btn_addInput:Button = itemView.findViewById(R.id.btn_addInput)

    }

}