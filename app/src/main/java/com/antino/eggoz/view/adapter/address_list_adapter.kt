package com.antino.eggoz.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.R
import com.antino.eggoz.ui.Buy.BuyFragment
import com.antino.eggoz.ui.profile.Model.FarmerProfile
import java.util.*

class address_list_adapter(var mcontext:BuyFragment,var result:List<FarmerProfile.FarmerInfo.Address>
) : RecyclerView.Adapter<address_list_adapter.ViewHolder>() {
    private var selectedPos = 0
    private lateinit var context: Context
    private lateinit var listItem: View


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem =
            layoutInflater.inflate(R.layout.address_list_view, parent, false)
        context = listItem.context

        return ViewHolder(listItem)
    }


    override fun getItemCount(): Int {
        return result.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_item_add_name.text = result[position].buildingAddress
        holder.txt_add_detail.text = "${result[position].streetAddress} ${result[position].landmark}" +
                " ${result[position].billingCity} ${result[position].pinCode}"

        holder.itemView.isSelected = selectedPos == position

        holder.layout_address.setOnClickListener {

            Log.d("data", "clicked $selectedPos pos $position")
            mcontext.updateAdd(result!![position].id)
            notifyItemChanged(selectedPos)
            selectedPos = position
            notifyItemChanged(selectedPos)
        }

        if(selectedPos == position) {

            holder.txt_item_add_name.setTextColor(Color.WHITE)
            holder.txt_add_detail.setTextColor(Color.WHITE)
            holder.layout_address.background=
                ResourcesCompat.getDrawable(context.resources, R.drawable.largeborder, null)

        }
        else {
            holder.txt_item_add_name.setTextColor(Color.BLACK)
            holder.txt_add_detail.setTextColor(ContextCompat.getColor(context,R.color.icon))
            holder.layout_address.background=
                ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.non_active_background,
                    null
                )
        }

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_item_add_name: TextView = itemView.findViewById(R.id.txt_address_title)
        val txt_add_detail: TextView = itemView.findViewById(R.id.txt_address_detail)
        val layout_address:RelativeLayout=itemView.findViewById(R.id.layout_address)




    }

}

