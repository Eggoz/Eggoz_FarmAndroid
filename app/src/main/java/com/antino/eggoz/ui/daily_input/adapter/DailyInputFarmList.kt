package com.antino.eggoz.ui.daily_input.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.ui.profile.Model.Farm
import java.text.SimpleDateFormat
import java.util.*

class DailyInputFarmList(
    private val contextmain: MainActivity,
    private val farmdata: List<String>,
    private val shedname: List<String>,
    private val flock: List<String>,
    private val flockid: List<Int>,
    private val lastupdate: List<String>,
    private val breedName: List<String>,
    private val flocktotalactivebird: List<Int>
) : RecyclerView.Adapter<DailyInputFarmList.ViewHolder>() {
    private lateinit var listItem: View

    private lateinit var layer:ArrayList<Farm.Result.Shed>
    private lateinit var grawer:ArrayList<Farm.Result.Shed>

    var ldate=""


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem=
            layoutInflater.inflate(R.layout.item_dailyfarm_list, parent, false)
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return farmdata.size
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_farmName.text=farmdata[position]
        holder.txt_shadeName.text=shedname[position]
        holder.txt_flockName.text=flock[position]

        val dfn = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        holder.txt_lastUpdateDate.text=lastupdate[position]

        holder.txt_lastUpdateDate.text="last updated: \n ${lastupdate[position]}"


        holder.txt_BreedName.text=breedName[position]

        val c = Calendar.getInstance().time


        println("Current time => $c")

        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate = df.format(c)

        ldate=formattedDate

        if (lastupdate[position]==null)
            ldate=df.toString()
        holder.btn_addInput.setOnClickListener { contextmain.dailyInputCallback(
            flockid[position],
            flocktotalactivebird[position],
            formattedDate
                ,"") }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txt_farmName: TextView = itemView.findViewById(R.id.txt_farm_name)
        val txt_shadeName: TextView = itemView.findViewById(R.id.txt_shade_name)
        val txt_flockName: TextView = itemView.findViewById(R.id.txt_flock_name)
        val txt_lastUpdateDate: TextView = itemView.findViewById(R.id.txt_last_update)
        val txt_BreedName: TextView = itemView.findViewById(R.id.txt_BreedName)


        val btn_addInput: Button = itemView.findViewById(R.id.btn_addInput)







    }

}