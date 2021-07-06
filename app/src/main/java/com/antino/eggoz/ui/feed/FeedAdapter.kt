package com.antino.eggoz.ui.feed

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.ui.feed.dialog.ImageLarge
import com.antino.eggoz.ui.feed.model.FeedData
import com.antino.eggoz.ui.home.dialog.NeccRateAll
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.text.SimpleDateFormat
import java.util.*


class FeedAdapter(
    private val mcontext: MainActivity,
    private val mcontext2: FeedFragment,
    private val fragmentManager: FragmentManager,
    private val result: List<FeedData.Result>?
) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {


    private lateinit var context: Context
    private lateinit var listItem: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listItem =
            layoutInflater.inflate(R.layout.item_feed_list, parent, false)
        return ViewHolder(listItem)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var dddate="${result!![position].publishAt}"
        holder.txt_vote.text="${result[position].stats.likes} Likes"

        dddate= dddate.substring(0,dddate.length-10)
        val format = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US
        )
        format.timeZone = TimeZone.getTimeZone("UTC")
        try {
            val date: Date = format.parse("${result[position].publishAt}")
            val sdf= SimpleDateFormat("dd MMM yyyy hh:mm aa")
            dddate=sdf.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("data","Exception ${e.message}")
        }


        holder.txttime_stamp.text = dddate

        if (result[position].isLiked)
            holder.btn_upvote.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_favorite_24, 0, 0, 0)

        holder.txtname.text = result[position].author.name
        holder.txtdetail.text = "${result[position].heading} \n${result[position].description}"

        Log.d("data","${ result[position].publishAt}")

        if (result[position].images!=null&& result[position].images?.size!! >0) {

            Glide.with(context)
                .asBitmap()
                .load(result[position].images?.get(0)?.image)
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.logo1)
                .into(holder.img_feed)

            Glide.with(context)
                .asBitmap()
                .load(result[position].author.image)
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.logo1)
                .into(holder.img_person)

        }

        holder.txtcomment.setOnClickListener {
            mcontext.loadComment(result[position].id,result[position].stats.likes)
        }

        holder.btn_upvote.setOnClickListener {
            if (!result[position].isLiked) {
                mcontext2.postlikedislike(result[position].id)
                result[position].isLiked=true
                holder.txt_vote.text="${result[position].stats.likes+1} Likes"
                holder.btn_upvote.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_favorite_24, 0, 0, 0)
            }
        }
        holder.btn_downvote.setOnClickListener {
            mcontext2.postlikedislike(result[position].id)
        }
        holder.img_layout.setOnClickListener {
            ImageLarge(result[position].images?.get(0)?.image, mcontext).show(
                mcontext.supportFragmentManager,
                "MyCustomFragment2"
            )
        }


    }

    override fun getItemCount(): Int {
        return result?.size!!
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtname: TextView = itemView.findViewById(R.id.txt_person_name)
        val txttime_stamp: TextView = itemView.findViewById(R.id.txt_time_stamp)
        val txtdetail: TextView = itemView.findViewById(R.id.txt_details)
        val txtcomment: TextView = itemView.findViewById(R.id.txt_comment)
        val txt_vote: TextView = itemView.findViewById(R.id.txt_vote)


        val img_person: ImageView = itemView.findViewById(R.id.img_person)
        val img_feed: ImageView = itemView.findViewById(R.id.img_feed)


        val btn_upvote: TextView = itemView.findViewById(R.id.img_voteup)
        val btn_downvote: ImageView = itemView.findViewById(R.id.img_votedown)
        val img_layout:CardView=itemView.findViewById(R.id.img_layout)


    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }


}