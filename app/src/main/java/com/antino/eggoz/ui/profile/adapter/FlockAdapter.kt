package com.antino.eggoz.ui.profile.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.ui.profile.Model.Farm
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class FlockAdapter(
    private val mcontex: MainActivity,
    private val flock: List<Farm.Result.Shed.Flock1>? = null,
    private val type: String
) : RecyclerView.Adapter<FlockAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var listItem: View
    private lateinit var newDate: Date


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem =
            layoutInflater.inflate(R.layout.item_flock_list, parent, false)
        context = listItem.context
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return flock?.size!!
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txt_flock_name.text = flock!![position].flockName
        holder.txt_breedname.text = flock[position].breed.breedName
        val week: Int = flock[position].age / 7
        val days: Int = flock[position].age % 7
        holder.txt_flockage.text = "${week}W ${days}D"
        holder.txt_Flock_quantity.text = flock[position].currentCapacity.toString()

        holder.cons_shade.visibility = View.VISIBLE

        if (flock[position].eggType == "") {
            holder.const_egg_type.visibility = View.GONE
        } else
            holder.txt_Egg_type.text = flock[position].eggType

        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        if (flock[position].lastDailyInputDate == null) {
            newDate = Calendar.getInstance().time
            holder.btn_FlockSummary.visibility = View.GONE
            holder.txt_last_update.text = "None"
            holder.const_egg_production.visibility = View.GONE
            holder.const_hdep.visibility = View.GONE
            holder.const_mortality.visibility = View.GONE
        } else {

            holder.txt_last_update.text = "Detail updated till: ${flock[position].lastDailyInputDate}"

            if (flock[position].lastDailyInputDate!=null){
                holder.txt_edt_dailyinput.visibility=View.VISIBLE
            }else{
                holder.txt_edt_dailyinput.visibility=View.GONE
            }

            holder.txt_edt_dailyinput.setOnClickListener {
                mcontex.loadDailyInputList(flock[position].id)
            }

            val date: Date =
                SimpleDateFormat("yyyy-MM-dd").parse("${flock[position].lastDailyInputDate}")
            newDate = Date(date.time + 1000 * 60 * 60 * 24)

            if (flock[position].dailyInputs?.size!! > 0) {
                holder.const_hdep.visibility = View.VISIBLE
                holder.const_mortality.visibility = View.VISIBLE
                val hdep: Double =
                    flock[position].dailyInputs!![flock[position].dailyInputs?.size!! - 1].eggDailyProduction.toDouble() /
                            flock[position].dailyInputs!![flock[position].dailyInputs?.size!! - 1].totalActiveBirds.toDouble()
                val df = DecimalFormat("####0.00")
                if (flock[position].dailyInputs!![flock[position].dailyInputs?.size!! - 1].eggDailyProduction == 0) {
                    holder.const_egg_production.visibility = View.GONE
                    holder.const_hdep.visibility = View.GONE
                }else{
                    holder.const_egg_production.visibility = View.VISIBLE
                    holder.const_hdep.visibility = View.VISIBLE
                }
                holder.txt_hdep.text = "${df.format(hdep * 100)} %"
                holder.txt_egg_production.text =
                    flock[position].dailyInputs!![flock[position].dailyInputs?.size!! - 1].eggDailyProduction.toString()
                holder.txt_mortality.text =
                    flock[position].dailyInputs!![flock[position].dailyInputs?.size!! - 1].mortality.toString()
            } else {
                holder.const_hdep.visibility = View.GONE
                holder.const_egg_production.visibility = View.GONE
                holder.const_mortality.visibility = View.GONE
            }


        }
        val formattedtodayDate = df.format(newDate)

        Log.d("data", "flock ${formattedtodayDate}")

        holder.btn_DailInput.setOnClickListener {
            mcontex.dailyInputCallback(
                flock[position].id,
                flock[position].initialCapacity,
                formattedtodayDate, type
            )
        }
        val c = Calendar.getInstance().time

        if (df.format(c).toString() == flock[position].lastDailyInputDate)
            holder.btn_DailInput.visibility = View.GONE

        holder.btn_flockSummary.setOnClickListener {
            mcontex.loadSummary(flock[position].id)
        }

        holder.btn_edtFlock.setOnClickListener {
            mcontex.loadEditFlock(flock[position].id)
        }


    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txt_flock_name: TextView = itemView.findViewById(R.id.txt_flock_name)

        val txt_breedname: TextView = itemView.findViewById(R.id.txt_BreedName)
        val txt_flockage: TextView = itemView.findViewById(R.id.txt_flockage)
        val txt_Flock_quantity: TextView = itemView.findViewById(R.id.txt_Flock_quantity)
        val txt_Egg_type: TextView = itemView.findViewById(R.id.txt_Egg_type)
        val txt_last_update: TextView = itemView.findViewById(R.id.txt_last_update)

        val btn_flockSummary: CardView = itemView.findViewById(R.id.btn_FlockSummary)
        val btn_DailInput: Button = itemView.findViewById(R.id.btn_DailyInput)

        val cons_shade: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)
        val flock_layout: ConstraintLayout = itemView.findViewById(R.id.flock_layout)


        val btn_FlockSummary: CardView = itemView.findViewById(R.id.btn_FlockSummary)
        val const_egg_type: ConstraintLayout = itemView.findViewById(R.id.const_egg_type)


        val const_hdep: ConstraintLayout = itemView.findViewById(R.id.const_hdep)
        val const_egg_production: ConstraintLayout =
            itemView.findViewById(R.id.const_egg_production)
        val const_mortality: ConstraintLayout = itemView.findViewById(R.id.const_mortality)

        val txt_hdep: TextView = itemView.findViewById(R.id.txt_Hdep)
        val txt_egg_production: TextView = itemView.findViewById(R.id.txt_egg_production)
        val txt_mortality: TextView = itemView.findViewById(R.id.txt_mortality)
        val txt_edt_dailyinput: TextView = itemView.findViewById(R.id.txt_edt_dailyinput)
        val btn_edtFlock: ImageView = itemView.findViewById(R.id.btn_edtFlock)


    }

}