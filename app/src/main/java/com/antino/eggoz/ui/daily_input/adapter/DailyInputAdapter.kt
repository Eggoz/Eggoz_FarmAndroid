package com.antino.eggoz.ui.daily_input.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.ui.daily_input.model.DailInput
import com.antino.eggoz.ui.profile.Model.Farm
import java.util.*

class DailyInputAdapter(
    private val contextmain: MainActivity,
    private val results: List<DailInput.Result>? = null
) : RecyclerView.Adapter<DailyInputAdapter.ViewHolder>() {
    private lateinit var listItem: View

    private lateinit var layer: ArrayList<Farm.Result.Shed>
    private lateinit var grawer: ArrayList<Farm.Result.Shed>

    var ldate = ""


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem =
            layoutInflater.inflate(R.layout.item_dailyinputlist, parent, false)
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return results?.size!!
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_Egg_Production.text = results!![position].eggDailyProduction.toString()
        holder.txt_Mortality.text = results[position].mortality.toString()
        holder.txt_feed.text = results[position].feed
        holder.txt_cull.text = results[position].culls.toString()
        holder.txt_broken_egg_in_production.text = results[position].brokenEggInProduction.toString()
        holder.txt_broken_egg_in_operation.text = results[position].brokenEggInOperation.toString()

        holder.txt_date.text= results[position].date

        holder.txt_Weight.text=results[position].weight

        if (results[position].weight.toDouble()!=0.0){
            holder.const_weight.visibility=View.VISIBLE
            holder.const2.visibility=View.GONE
            holder.const_broken_egg_in_production.visibility=View.GONE
            holder.const_broken_egg_in_operation.visibility=View.GONE
            holder.const_EggProduction.visibility=View.GONE
        }else{
            holder.const_weight.visibility=View.GONE
            holder.const_broken_egg_in_production.visibility=View.VISIBLE
            holder.const_broken_egg_in_operation.visibility=View.VISIBLE
            holder.const_EggProduction.visibility=View.VISIBLE
        }
        if (results[position].culls==0){
            holder.const_cull.visibility=View.GONE
        }

        holder.btn_editSubmit.setOnClickListener {
            contextmain.loadUpdateDailtInput(results[position])
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_Egg_Production: TextView = itemView.findViewById(R.id.txt_Egg_Production)
        val txt_Mortality: TextView = itemView.findViewById(R.id.txt_Mortality)
        val txt_feed: TextView = itemView.findViewById(R.id.txt_feed)
        val txt_cull: TextView = itemView.findViewById(R.id.txt_cull)
        val txt_broken_egg_in_production: TextView =
            itemView.findViewById(R.id.txt_broken_egg_in_production)
        val txt_broken_egg_in_operation: TextView =
            itemView.findViewById(R.id.txt_broken_egg_in_operation)
        val txt_date: TextView =
            itemView.findViewById(R.id.txt_date)
        val txt_Weight: TextView =
            itemView.findViewById(R.id.txt_Weight)
        val const_weight: ConstraintLayout =
            itemView.findViewById(R.id.const_weight)
        val const_broken_egg_in_production: ConstraintLayout =
            itemView.findViewById(R.id.const_broken_egg_in_production)
        val const_broken_egg_in_operation: ConstraintLayout =
            itemView.findViewById(R.id.const_broken_egg_in_operation)
        val const_EggProduction: ConstraintLayout =
            itemView.findViewById(R.id.const_EggProduction)
        val const_cull: ConstraintLayout =
            itemView.findViewById(R.id.const_cull)
        val btn_editSubmit: Button =
            itemView.findViewById(R.id.btn_editSubmit)
        val const2: ConstraintLayout =
            itemView.findViewById(R.id.const2)
    }

}