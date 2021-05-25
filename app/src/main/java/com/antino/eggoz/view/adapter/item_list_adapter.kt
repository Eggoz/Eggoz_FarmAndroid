package com.antino.eggoz.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class item_list_adapter(

    private var id :Int,
    private var name :String,
    private var img:String,
    private var price:String,
    private var qnt :Int,
    private var des:String,
    private var tax:Boolean
    /*
    var img: ArrayList<String>,
    var item_name: ArrayList<String>,
    var item_price: ArrayList<String>*/
) : RecyclerView.Adapter<item_list_adapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var listItem: View


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem =
            layoutInflater.inflate(R.layout.item_list_view, parent, false)
        context = listItem.context
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return 1
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_item_name.text = name
        holder.txt_item_price.text = "Rs.${price}"

        Glide.with(context)
            .asBitmap()
            .load(img)
            .centerInside()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logo1)
            .into(holder.img_item)

     /*   Picasso.get().load(img).networkPolicy(NetworkPolicy.NO_CACHE)
            .error(R.drawable.logo1).into(holder.img_item)*/
        holder.btn_add.setOnClickListener{
            holder.btn_add.visibility = View.GONE
            qnt=1
            holder.btn_inc.visibility = View.VISIBLE
            holder.txt_item_qnt.text = qnt.toString()
        }
        holder.txt_item_min.setOnClickListener{
            qnt = Integer.parseInt(holder.txt_item_qnt.text as String)
            if (qnt != 0)
                qnt--
            else
                holder.txt_item_qnt.text = "00"
            if (qnt<=0){
                holder.btn_add.visibility = View.VISIBLE
                holder.btn_inc.visibility = View.GONE
            }
            holder.txt_item_qnt.text = qnt.toString()
        }
        holder.txt_item_add.setOnClickListener {
            qnt = Integer.parseInt(holder.txt_item_qnt.getText() as String)
            qnt++
            holder.txt_item_qnt.text = qnt.toString()
        }

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txt_item_name: TextView = itemView.findViewById(R.id.txt_item_name)
        val txt_item_price: TextView = itemView.findViewById(R.id.txt_item_price)
        val img_item: ImageView = itemView.findViewById(R.id.img_leadger)
        val btn_add: LinearLayout = itemView.findViewById(R.id.btn_Add)
        val btn_inc:LinearLayout = itemView.findViewById(R.id.ll_addQuan_incremeter)
        val txt_item_min: TextView = itemView.findViewById(R.id.minus)
        val txt_item_add: TextView = itemView.findViewById(R.id.plus)
        val txt_item_qnt: TextView = itemView.findViewById(R.id.txtQuan)


    }

}