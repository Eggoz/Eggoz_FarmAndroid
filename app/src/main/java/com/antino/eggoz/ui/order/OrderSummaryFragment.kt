package com.antino.eggoz.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.MainActivity
import com.antino.eggoz.databinding.FragmentOrderSummaryBinding
import com.antino.eggoz.ui.order.adapter.OrderSummaryAdapter

class OrderSummaryFragment(val context:MainActivity) : Fragment(),orderCallback {
    private lateinit var binding: FragmentOrderSummaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderSummaryBinding.inflate(inflater, container, false)

        val img:ArrayList<String> = ArrayList()
        val type:ArrayList<String> = ArrayList()
        val eggozprice:ArrayList<String> = ArrayList()

        img.add("https://3.imimg.com/data3/VE/QY/MY-7539096/white-egg-500x500.jpg")
        img.add("https://img2.mashed.com/img/gallery/how-are-brown-and-white-eggs-different/birds-of-a-feather-1497626048.jpg")
        img.add("https://3.imimg.com/data3/VE/QY/MY-7539096/white-egg-500x500.jpg")
        img.add("https://img2.mashed.com/img/gallery/how-are-brown-and-white-eggs-different/birds-of-a-feather-1497626048.jpg")
        type.add("white egg")
        type.add("brown egg")
        type.add("white egg")
        type.add("brown egg")
        eggozprice.add("1221")
        eggozprice.add("1221")
        eggozprice.add("1221")
        eggozprice.add("1221")



        binding.recycleviewOrderSummary.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleviewOrderSummary.itemAnimator = DefaultItemAnimator()
        binding.recycleviewOrderSummary.isNestedScrollingEnabled = false
        binding.recycleviewOrderSummary.adapter= OrderSummaryAdapter(this,img, type, eggozprice,eggozprice)


        return binding.root
    }

    override fun ordertrack() {
        context.loadTrackOrder()
    }

}