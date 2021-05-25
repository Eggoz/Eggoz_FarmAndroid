package com.antino.eggoz.ui.profile.adapter

import android.annotation.SuppressLint
import android.content.Context
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

class ShadeAdapter(
    private val contextmain: MainActivity,
    private val shadedata: ArrayList<Farm.Result.Shed>
) : RecyclerView.Adapter<ShadeAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var listItem: View
    private lateinit var adapter:FlockAdapter


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem =
            layoutInflater.inflate(R.layout.item_shades_list, parent, false)
        context = listItem.context
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return shadedata.size
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.txt_shade_name.text=shadedata[position].shedName
        holder.txt_type.text="Type: "+shadedata[position].shedType
        holder.txt_shade_capacity.text="Capacity: "+shadedata[position].totalActiveBirdCapacity.toString()
        holder.flockrecycleview.layoutManager =  LinearLayoutManager(
            contextmain,
            LinearLayoutManager.VERTICAL,
            false
        )
        holder.flockrecycleview.itemAnimator = DefaultItemAnimator()
        holder.flockrecycleview.isNestedScrollingEnabled = false
        if (shadedata[position].flocks?.size!!>0)
        adapter= FlockAdapter(contextmain,shadedata[position].flocks,shadedata[position].shedType)
        holder.flockrecycleview.adapter =adapter

        holder.btn_add_flock.setOnClickListener {

            contextmain.AddFlock(shadedata[position].id,"")
        }



        holder.cons_shade.visibility=View.VISIBLE


    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txt_shade_name: TextView = itemView.findViewById(R.id.txt_shade_name)
        val txt_type: TextView = itemView.findViewById(R.id.txt_type)
        val txt_shade_capacity: TextView = itemView.findViewById(R.id.txt_shade_capacity)

        val btn_add_flock: Button = itemView.findViewById(R.id.txt_add_flock)
        val cons_shade: ConstraintLayout = itemView.findViewById(R.id.shade_details_layout)
        val flockrecycleview:RecyclerView=itemView.findViewById(R.id.recycle_view_flock)


    }

}