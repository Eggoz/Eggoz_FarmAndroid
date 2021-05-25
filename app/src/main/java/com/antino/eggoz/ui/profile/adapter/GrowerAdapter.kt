package com.antino.eggoz.ui.profile.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.ui.profile.Model.Farm
import com.antino.eggoz.ui.profile.Model.Grower
import kotlin.math.min

class GrowerAdapter(
    private val contextmain: MainActivity,
    private val growerdata: ArrayList<Farm.Result.Shed>
) : RecyclerView.Adapter<GrowerAdapter.ViewHolder>() {
    private lateinit var listItem: View
    private lateinit var adapter:FlockAdapter


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem =
            layoutInflater.inflate(R.layout.item_grower_list, parent, false)
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return growerdata.size
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.txt_grower_name.text=growerdata[position].shedName
        holder.txt_type.text="Type: ${growerdata[position].shedType}"
        holder.txt_grower_capacity.text="Capacity: ${growerdata[position].totalActiveBirdCapacity}"

        holder.flockrecycleview.layoutManager =  LinearLayoutManager(
            contextmain,
            LinearLayoutManager.VERTICAL,
            false
        )
        holder.flockrecycleview.itemAnimator = DefaultItemAnimator()
        holder.flockrecycleview.isNestedScrollingEnabled = false
        if (growerdata[position].flocks?.size!!>0)
        adapter= FlockAdapter(contextmain,growerdata[position].flocks,growerdata[position].shedType)
        holder.flockrecycleview.adapter =adapter

        holder.btn_add_flock.setOnClickListener {

            contextmain.AddFlock(growerdata[position].id,growerdata[position].shedType)
        }



    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_grower_name: TextView = itemView.findViewById(R.id.txt_grower_name)
        val txt_type: TextView = itemView.findViewById(R.id.txt_type)
        val txt_grower_capacity: TextView = itemView.findViewById(R.id.txt_grower_capacity)

        val cons_grower: ConstraintLayout = itemView.findViewById(R.id.grower_details_layout)

        val flockrecycleview:RecyclerView=itemView.findViewById(R.id.recycle_view_flock)


        val btn_add_flock: Button = itemView.findViewById(R.id.txt_add_flock)


    }

}