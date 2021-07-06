package com.antino.eggoz.ui.profile.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.ui.profile.Model.Farm
import com.antino.eggoz.utils.Constants
import com.antino.eggoz.utils.PrefrenceUtils
import kotlin.math.min

class FarmAdapter(
    private val contextmain: MainActivity,
    private val farmdata: List<Farm.Result>?
) : RecyclerView.Adapter<FarmAdapter.ViewHolder>() {
    private lateinit var listItem: View

    private lateinit var layer: ArrayList<Farm.Result.Shed>
    private lateinit var grawer: ArrayList<Farm.Result.Shed>


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem =
            layoutInflater.inflate(R.layout.item_farm_list, parent, false)
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return farmdata?.size!!
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("data", "farmadapter " + farmdata!![position].farmName)

        holder.btn_edtFarm.setOnClickListener {
            contextmain.loadEditFarm(farmdata[position].id)
        }

        setTextva(holder.txt_farmName, farmdata[position].farmName)
        val add =
            "${farmdata[position].billingAddress.buildingAddress} ${farmdata[position].billingAddress.landmark} ${farmdata[position].billingAddress.billingCity} ${farmdata[position].billingAddress.streetAddress} ${farmdata[position].billingAddress.pinCode}"
        setTextva(holder.txtfarmadd, add)

        layer = ArrayList()
        grawer = ArrayList()

        for (i in farmdata[position].sheds?.indices!!) {
            if (farmdata[position].sheds!![i].shedType == "Layer") {
                layer.add(farmdata[position].sheds!![i])
            } else {
                grawer.add(farmdata[position].sheds!![i])
            }
        }

        holder.farm_layout_head.setOnClickListener {
            if (holder.cons_farm_detail.isVisible)
                holder.cons_farm_detail.visibility = View.GONE
            else holder.cons_farm_detail.visibility = View.VISIBLE

        }

        val adapter: ShadeAdapter
        val growerAdapter: GrowerAdapter

        if (layer.size != 0) {
            holder.recyclerView.layoutManager = LinearLayoutManager(
                contextmain,
                LinearLayoutManager.VERTICAL,
                false
            )
            holder.recyclerView.itemAnimator = DefaultItemAnimator()
            holder.recyclerView.isNestedScrollingEnabled = false
            adapter = ShadeAdapter(contextmain, layer)
            holder.recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()

        } else {
            holder.recyclerView.visibility = View.GONE
        }





        if (grawer.size != 0) {
            holder.recycleviewgrower.layoutManager = LinearLayoutManager(
                contextmain,
                LinearLayoutManager.VERTICAL,
                false
            )

            holder.recycleviewgrower.itemAnimator = DefaultItemAnimator()
            holder.recycleviewgrower.isNestedScrollingEnabled = false
            growerAdapter = GrowerAdapter(
                contextmain, grawer
            )
            holder.recycleviewgrower.adapter = growerAdapter
            growerAdapter.notifyDataSetChanged()
        } else {
            holder.recycleviewgrower.visibility = View.GONE
        }

        holder.txtfarmaddshade.setOnClickListener {
//            contextmain.loadAddLayer(farmdata[position].id)

//            contextmain.loadAddLayer(farmdata[position].id,far)
            if (farmdata[position].farmLayerType == "Broiler") {
                contextmain.loadAddLayer(farmdata[position].id, "broiler")
            } else if (farmdata[position].farmLayerType == "Layer") {
                contextmain.loadAddLayer(farmdata[position].id, "layer")


            }
//            OptionSelect(contextmain,farmdata[position].id).show(contextmain.supportFragmentManager, "MyCustomFragment2")
        }

    }


    private fun setTextva(textView: TextView, s: String) {

        if (s.length > 20)
            textView.text = s.substring(0, min(s.length, 20)) + " ..."
        else
            textView.text = s.substring(0, min(s.length, 20))

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txt_farmName: TextView = itemView.findViewById(R.id.txt_farm_name)
        val txtfarmadd: TextView = itemView.findViewById(R.id.txt_farm_add)
        val txtfarmaddshade: TextView = itemView.findViewById(R.id.txt_addshades)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recyceleview_farm_detail)
        val recycleviewgrower: RecyclerView = itemView.findViewById(R.id.recyceleview_grower_detail)
        val farm_layout_head: ConstraintLayout = itemView.findViewById(R.id.farm_layout_head)
        val cons_farm_detail: ConstraintLayout = itemView.findViewById(R.id.cons_farm_detail)
        val btn_edtFarm: ImageView = itemView.findViewById(R.id.btn_edtFarm)


    }

}