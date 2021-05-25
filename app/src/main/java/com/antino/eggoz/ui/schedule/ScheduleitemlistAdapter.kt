package com.antino.eggoz.ui.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.R
import java.util.*

class ScheduleitemlistAdapter(val callbacks2: ScheduleDetailFragment,
    private var pos: ArrayList<String>,
    private var qnt: ArrayList<String>,
    private var rate: ArrayList<String>,
    private var totalamt: ArrayList<String>,
    private var remark: ArrayList<String>
) : RecyclerView.Adapter<ScheduleitemlistAdapter.ViewHolder>() {
    lateinit var listItem: View
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem= layoutInflater.inflate(R.layout.item_schedule, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txtproductname.text = pos[position]
        holder.txtqnt.text = qnt[position]
        holder.txtrate.text = rate[position]
        holder.txttotal.text = totalamt[position]
        holder.txt_remark.text = remark[position]

        holder.img_edit.setOnClickListener {
            callbacks2.custom_dialog(position)
        }
    }

    override fun getItemCount(): Int {
        return pos.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtproductname: TextView = itemView.findViewById(R.id.txt_product_name)
        val txtqnt: TextView = itemView.findViewById(R.id.txt_quantity_name)
        val txtrate: TextView = itemView.findViewById(R.id.txt_rate_name)
        val txttotal: TextView = itemView.findViewById(R.id.txt_total_amount_name)
        val txt_remark: TextView = itemView.findViewById(R.id.txt_remark_name)

        val img_edit: ImageView = itemView.findViewById(R.id.img_edit)


    }

}