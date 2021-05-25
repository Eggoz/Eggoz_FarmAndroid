package com.antino.eggoz.ui.item

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
import com.antino.eggoz.room.CartDao
import com.antino.eggoz.room.RoomCart
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class CartLargelistAdapter(
    private val cart:List<RoomCart>,
    private var cartdao: CartDao

/*
    private val img: ArrayList<String>,
    private val item_name: ArrayList<String>,
    private val item_des: ArrayList<String>,
    private val item_status: ArrayList<String>,
    private val item_price: ArrayList<String>*/
) : RecyclerView.Adapter<CartLargelistAdapter.ViewHolder>() {


    private lateinit var context: Context
    private lateinit var listItem: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem=
            layoutInflater.inflate(R.layout.item_cart_large_list, parent, false)
        context = listItem.context
        return ViewHolder(listItem)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtitemname.text = cart[position].name

        holder.txtitemprice.text = "Rs.${cart[position].price}"


        Glide.with(context)
            .asBitmap()
            .load(cart[position].img)
            .centerInside()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logo1)
            .into(holder.imgitem)

//        holder.txtitemdes.text=cart[position].des
        holder.txtitemdes.visibility=View.GONE

        if (cart[position].status!!) {
            holder.txtitemstatus.text = "Eligible For Shipping"
        }else holder.txtitemstatus.text = "Not Eligible For Shipping"


        if (cart[position].quantaty!!>0) {
            holder.add.visibility=View.GONE
            holder.incre.visibility=View.VISIBLE
            holder.txt_item_qnt.text = cart[position].quantaty.toString()
        }

        holder.add.setOnClickListener{
            holder.qnt=1
            holder.add.visibility=View.GONE
            holder.incre.visibility=View.VISIBLE
            holder.txt_item_qnt.text = holder.qnt.toString()

            cartdao.updateCart(cart[position].id,holder.qnt,"${cart[position].price}0")

        }

        holder.txt_item_add.setOnClickListener {
            holder.qnt = Integer.parseInt(holder.txt_item_qnt.text as String)
            holder.qnt++
            holder.txt_item_qnt.text = holder.qnt.toString()
            cartdao.updateCart(cart[position].id, holder.qnt, "${cart[position].price}0")
        }
        holder.txt_item_min.setOnClickListener {
            holder.qnt = Integer.parseInt(holder.txt_item_qnt.text as String)
            if (holder.qnt!=0)
                holder.qnt--
            else {
                holder.txt_item_qnt.text = "00"
                cartdao.deletebyid(cart[position].id)
            }
            if (holder.qnt<=0){
                holder.add.visibility=View.VISIBLE
                holder.incre.visibility=View.GONE
                cartdao.deletebyid(cart[position].id)
                listItem.visibility=View.GONE
                notifyDataSetChanged()
            }else {
                cartdao.updateCart(cart[position].id, holder.qnt, "${cart[position].price}0")
            }
            holder.txt_item_qnt.text = holder.qnt.toString()
        }

        holder.img_delete.setOnClickListener {
            cartdao.deletebyid(cart[position].id)
            listItem.visibility=View.GONE
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return cart.size
    }



    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        var qnt=0

        val txtitemname: TextView = itemView.findViewById(R.id.txt_item_name)
        val txtitemprice: TextView = itemView.findViewById(R.id.txt_item_price)
        val txtitemdes: TextView = itemView.findViewById(R.id.txt_item_detail_des)
        val txtitemstatus: TextView = itemView.findViewById(R.id.txt_status)

        val add:LinearLayout=itemView.findViewById(R.id.btn_Add)
        val incre:LinearLayout = itemView.findViewById(R.id.ll_addQuan_incremeter)

        val txt_item_min: TextView = itemView.findViewById(R.id.minus)
        val txt_item_add: TextView = itemView.findViewById(R.id.plus)
        val txt_item_qnt: TextView = itemView.findViewById(R.id.txtQuan)

        val imgitem: ImageView = itemView.findViewById(R.id.img_leadger)
        val img_delete: ImageView = itemView.findViewById(R.id.item_delete)



    }




}