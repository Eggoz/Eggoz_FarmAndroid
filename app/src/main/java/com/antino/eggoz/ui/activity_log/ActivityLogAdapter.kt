package com.antino.eggoz.ui.activity_log

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.R
import java.util.*

class ActivityLogAdapter(
    val id:ArrayList<Int> = ArrayList(),
    val date:ArrayList<String> = ArrayList(),
    val quantity:ArrayList<Int> = ArrayList(),
    val amount:ArrayList<Double> = ArrayList(),
    val remark:ArrayList<String> = ArrayList(),
    val farmerid:ArrayList<Int> = ArrayList(),
    val partyid:ArrayList<Int> = ArrayList(),
    val productSubDivision:ArrayList<Int> = ArrayList()
) : RecyclerView.Adapter<ActivityLogAdapter.ViewHolder>() {


    private lateinit var context: Context
    private lateinit var listItem: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem =
            layoutInflater.inflate(R.layout.item_logs_list, parent, false)
        context = listItem.context
        return ViewHolder(listItem)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txtupdate_logs.text=date[position]
        holder.txtfarm_logs.text=farmerid[position].toString()
        holder.txt_date_logs.text=date[position]
        holder.txtitem_logs.text=remark[position]
        holder.txtquantity_logs.text=quantity[position].toString()
        holder.txtrate_logs.text=partyid[position].toString()
        holder.txtdiscount_logs.text=productSubDivision[position].toString()


    }

    override fun getItemCount(): Int {
        return date.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtupdate_logs: TextView = itemView.findViewById(R.id.txt_update_on_logs)
        val txtfarm_logs: TextView = itemView.findViewById(R.id.txt_farm_logs)
        val txt_date_logs: TextView = itemView.findViewById(R.id.txt_date_logs)
        val txtitem_logs: TextView = itemView.findViewById(R.id.txt_item_logs)
        val txtquantity_logs: TextView = itemView.findViewById(R.id.txt_quantity_logs)
        val txtrate_logs: TextView = itemView.findViewById(R.id.txt_rate_logs)
        val txtdiscount_logs: TextView = itemView.findViewById(R.id.txt_discount_logs)


    }


}