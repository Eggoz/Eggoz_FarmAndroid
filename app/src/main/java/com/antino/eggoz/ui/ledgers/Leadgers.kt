package com.antino.eggoz.ui.ledgers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.databinding.FragmentLeadgersBinding

class Leadgers : Fragment() {

    private lateinit var binding: FragmentLeadgersBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeadgersBinding.inflate(inflater, container, false)
        val img:ArrayList<String> = ArrayList()
        val type:ArrayList<String> = ArrayList()
        val balance:ArrayList<String> = ArrayList()

        img.add("http://i.imgur.com/DvpvklR.png")
        img.add("http://i.imgur.com/DvpvklR.png")
        img.add("http://i.imgur.com/DvpvklR.png")
        type.add("white egg")
        type.add("white egg")
        type.add("white egg")
        balance.add("-1222")
        balance.add("-1222")
        balance.add("4566")

        binding.recyclerviewLeader.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerviewLeader.itemAnimator = DefaultItemAnimator()
        binding.recyclerviewLeader.isNestedScrollingEnabled = false
        binding.recyclerviewLeader.adapter= LeadgerAdapter(img, type, img,balance)

        return binding.root
    }
}