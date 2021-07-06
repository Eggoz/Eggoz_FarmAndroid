package com.antino.eggoz.ui.daily_input.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.R
import com.antino.eggoz.ui.daily_input.DailyInputDetailFragment

class FlockmedlistAdapter(val callbacks: DailyInputDetailFragment,
                          private val flockmedid:ArrayList<Int>,
                          private val flockmedqnt:ArrayList<Int>,
                          private val flockmedname:ArrayList<String>
) : RecyclerView.Adapter<FlockmedlistAdapter.ViewHolder>() {
    lateinit var listItem: View
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem= layoutInflater.inflate(R.layout.item_flock_med_list, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txt_flockmedName.text="${flockmedname[position]} :"
        holder.txt_flockmedqnt.text="${flockmedqnt[position]} mg"

        holder.img_edit.setOnClickListener {
//            callbacks.updateposion(position)
            callbacks.mededitCallback(position,flockmedqnt[position],flockmedid[position] )
        }
    }

    override fun getItemCount(): Int {
        return flockmedname.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txt_flockmedName: TextView = itemView.findViewById(R.id.txt_MedName)
        val txt_flockmedqnt: TextView = itemView.findViewById(R.id.txt_MedQnt)
        val img_edit: ImageView = itemView.findViewById(R.id.img_edit)


    }

}