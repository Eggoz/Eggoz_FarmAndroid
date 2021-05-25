package com.antino.eggoz.ui.feed

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.R
import com.antino.eggoz.ui.feed.model.Comment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.util.*

class CommentAdapter(
    private val mcontext: CommentFragment,

    var results: List<Comment.ResultComment>? = null
    /*
    private val img_pic: ArrayList<String>,
    private val name: ArrayList<String>,
    private val commentDetail: ArrayList<String>*/
) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {


    private lateinit var context: Context
    private lateinit var listItem: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem =
            layoutInflater.inflate(R.layout.item_comment_list, parent, false)
        context = listItem.context
        return ViewHolder(listItem)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txtname.text = results!![position].user.name
        holder.txtcomment_detail.text = results!![position].commentText
        if (results!![position].isLiked)
            holder.likeComment.setTextColor(ContextCompat.getColor(context,R.color.app_color))


        Glide.with(context)
            .asBitmap()
            .load(results!![position].user.image)
            .centerInside()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logo1)
            .into(holder.img_person)

        holder.likeComment.setOnClickListener { mcontext.likedislike(results!![position].id) }
//        holder.txt_reply.setOnClickListener { bottom.reply(position, results!![position].user.name) }


    }

    override fun getItemCount(): Int {
        if (results == null)
            return 0
        else
            return results?.size!!
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtname: TextView = itemView.findViewById(R.id.txt_name)
        val txtcomment_detail: TextView = itemView.findViewById(R.id.txt_comment_detail)
        val img_person: ImageView = itemView.findViewById(R.id.img_pic)
        val likeComment: TextView = itemView.findViewById(R.id.likeComment)
        val txt_reply: TextView = itemView.findViewById(R.id.txt_reply)


    }


}