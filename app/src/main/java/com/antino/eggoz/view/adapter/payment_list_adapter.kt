package com.antino.eggoz.view.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.R
import java.util.ArrayList

class payment_list_adapter(
    private var item_name: ArrayList<String>,
    private var item_detail: ArrayList<String>
) : RecyclerView.Adapter<payment_list_adapter.ViewHolder>() {
    //    private var selectedPos = RecyclerView.NO_POSITION
    private var selectedPos = 0
    private lateinit var context: Context
    private lateinit var listItem: View

    // TODO: 09-03-2021 remove this file


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem =
            layoutInflater.inflate(R.layout.payment_list_view, parent, false)
        context = listItem.context

        return ViewHolder(listItem)
    }


    override fun getItemCount(): Int {
        return item_name.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_card_name.text = item_name[position]
        holder.txt_card_number.text = item_detail[position]
        holder.img_card.setImageResource(R.drawable.logo1)

        holder.layout_address.setOnClickListener {

            Log.d("data", "clicked $selectedPos pos $position")

            notifyItemChanged(selectedPos)
            selectedPos = position
            notifyItemChanged(selectedPos)
        }

        if (selectedPos == position) {

            holder.txt_card_name.setTextColor(Color.WHITE)
            holder.txt_card_number.setTextColor(Color.WHITE)
            holder.layout_address.background =
                ResourcesCompat.getDrawable(context.resources, R.drawable.largeborder, null)

        } else {
            holder.txt_card_name.setTextColor(ContextCompat.getColor(context, R.color.icon))
            holder.txt_card_number.setTextColor(ContextCompat.getColor(context, R.color.icon))
            holder.layout_address.background =
                ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.non_active_background,
                    null
                )
        }


    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_card_name: TextView = itemView.findViewById(R.id.txt_card_name)
        val txt_card_number: TextView = itemView.findViewById(R.id.txt_card_number)
        val img_card: ImageView = itemView.findViewById(R.id.img_card_)
        val layout_address: RelativeLayout = itemView.findViewById(R.id.layout_card_list)


    }

}

